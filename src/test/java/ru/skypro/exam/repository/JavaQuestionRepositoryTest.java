package ru.skypro.exam.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skypro.exam.constant.QuestionsConstants.*;

@ExtendWith(SpringExtension.class)
class JavaQuestionRepositoryTest {
    private HashMap<String, String> questionsMap;
    private HashSet<Question> questions;
    private JavaQuestionRepository repository;

    @BeforeEach
    public void setUp() {
        questions = new HashSet<>(JAVA_LIST);
        questionsMap = new HashMap<>();
        for (Question question : questions) {
            questionsMap.put(question.getQuestion(), question.getAnswer());
        }
        repository = new JavaQuestionRepository();
        for (Question question : JAVA_LIST) {
            try {
                repository.addQuestion(question);
            } catch (QuestionAlreadyExistsException e) {
            }
        }
    }

    @Test
    @DisplayName("Тестирование добавления нового вопроса")
    public void shouldAddNewQuestion() throws QuestionAlreadyExistsException, NoSuchFieldException, IllegalAccessException {
        Question newQuestion = new Question(Q_J_6, Q_J_6);
        Question addedQuestion = repository.addQuestion(newQuestion);
        assertEquals(newQuestion, addedQuestion);

        Field questionsField = JavaQuestionRepository.class.getDeclaredField("questions");
        questionsField.setAccessible(true);
        Map<String, String> internalQuestions = (Map<String, String>) questionsField.get(repository);
        assertTrue(internalQuestions.containsKey(newQuestion.getQuestion()));
        assertEquals(newQuestion.getAnswer(), internalQuestions.get(newQuestion.getQuestion()));
    }

    @Test
    @DisplayName("Тестирование дублирования вопроса")
    public void shouldThrowQuestionAlreadyExistsException() throws QuestionAlreadyExistsException {

        assertThrows(QuestionAlreadyExistsException.class, () -> repository.addQuestion(Q_J_1, A_J_1));
    }

    @Test
    @DisplayName("Тестирование удаления вопроса")
    public void shouldRemoveQuestion() throws QuestionNotExistsException, NoSuchFieldException, IllegalAccessException, QuestionAlreadyExistsException {
        Question removedQuestion = repository.removeQuestion(JAVA1);

        Field questionsField = JavaQuestionRepository.class.getDeclaredField("questions");
        questionsField.setAccessible(true);
        Map<String, String> internalQuestions = (Map<String, String>) questionsField.get(repository);
        assertFalse(internalQuestions.containsKey(removedQuestion.getQuestion()));
    }

    @Test
    @DisplayName("Тестирование удаления несуществующего вопроса")
    public void shouldThrowQuestionNotExistsException() {
        assertThrows(QuestionNotExistsException.class, () -> repository.removeQuestion(JAVA6));
    }

    @Test
    @DisplayName("Тестирование получения всех вопросов")
    public void shouldGetAllQuestions() {
        Collection<Question> allQuestions = repository.getAllQuestions();
        assertEquals(JAVA_LIST.size(), allQuestions.size());
        assertTrue(allQuestions.containsAll(JAVA_LIST));
    }
}
