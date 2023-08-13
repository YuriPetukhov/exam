package ru.skypro.exam.repository;

import org.junit.jupiter.api.*;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skypro.exam.constant.QuestionsConstants.*;

class JavaQuestionRepositoryTest {
    private static final HashMap<String, String> questionsMap = new HashMap<>();
    private static final HashSet<Question> questions = new HashSet<>(JAVA_LIST);
    private static JavaQuestionRepository repository;

    @BeforeAll
    public static void setUpClass() throws QuestionAlreadyExistsException {
        questionsMap.putAll(createQuestionMap(questions));
        repository = new JavaQuestionRepository();
        initializeRepository(repository, questions);
    }
    @BeforeEach
    public void beforeEach() throws QuestionAlreadyExistsException {
        repository = new JavaQuestionRepository();
        initializeRepository(repository, questions);
    }
    @AfterEach
    public void afterEach() {
        repository = null;
    }
    private static Map<String, String> createQuestionMap(Set<Question> inputQuestions) {
        Map<String, String> map = new HashMap<>();
        for (Question question : inputQuestions) {
            map.put(question.getQuestion(), question.getAnswer());
        }
        return map;
    }
    private static void initializeRepository(JavaQuestionRepository repository, Set<Question> inputQuestions) throws QuestionAlreadyExistsException {
        for (Question question : inputQuestions) {
            repository.addQuestion(question);
        }
    }
    @Test
    @DisplayName("Тест добавления нового вопроса")
    public void shouldAddNewQuestion() throws QuestionAlreadyExistsException, NoSuchFieldException, IllegalAccessException {
        Question newQuestion = new Question(Q_J_6, A_J_6);
        Question addedQuestion = repository.addQuestion(newQuestion);
        assertEquals(newQuestion, addedQuestion);

        Field questionsField = JavaQuestionRepository.class.getDeclaredField("questions");
        questionsField.setAccessible(true);
        Map<String, String> internalQuestions = (Map<String, String>) questionsField.get(repository);
        assertTrue(internalQuestions.containsKey(newQuestion.getQuestion()));
        assertEquals(newQuestion.getAnswer(), internalQuestions.get(newQuestion.getQuestion()));
    }

    @Test
    @DisplayName("Тест дублирования вопроса")
    public void shouldThrowQuestionAlreadyExistsException() {
        assertThrows(QuestionAlreadyExistsException.class, () -> repository.addQuestion(Q_J_1, A_J_1));
    }
    @Test
    @DisplayName("Тест удаления вопроса")
    public void shouldRemoveQuestion() throws QuestionNotExistsException, NoSuchFieldException, IllegalAccessException, QuestionAlreadyExistsException {
        Question removedQuestion = repository.removeQuestion(JAVA1);

        Field questionsField = JavaQuestionRepository.class.getDeclaredField("questions");
        questionsField.setAccessible(true);
        Map<String, String> internalQuestions = (Map<String, String>) questionsField.get(repository);
        assertFalse(internalQuestions.containsKey(removedQuestion.getQuestion()));
    }
    @Test
    @DisplayName("Тест удаления несуществующего вопроса")
    public void shouldThrowQuestionNotExistsException() {
        assertThrows(QuestionNotExistsException.class, () -> repository.removeQuestion(JAVA6));
    }
    @Test
    @DisplayName("Тест получения всех вопросов")
    public void shouldGetAllQuestions() {
        Collection<Question> allQuestions = repository.getAllQuestions();
        assertEquals(JAVA_LIST.size(), allQuestions.size());
        assertTrue(allQuestions.containsAll(JAVA_LIST));
    }
}
