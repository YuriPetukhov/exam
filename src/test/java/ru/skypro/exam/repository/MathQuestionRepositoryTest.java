package ru.skypro.exam.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skypro.exam.constant.QuestionsConstants.*;

class MathQuestionRepositoryTest {
    private HashMap<String, String> questionsMap;
    private HashSet<Question> questions;
    private MathQuestionRepository repository;
    @BeforeEach
    public void setUp() {
        questions = new HashSet<>(MATH_LIST);
        questionsMap = new HashMap<>();
        for (Question question : questions) {
            questionsMap.put(question.getQuestion(), question.getAnswer());
        }
        repository = new MathQuestionRepository();
        for (Question question : MATH_LIST) {
            try {
                repository.addQuestion(question);
            } catch (QuestionAlreadyExistsException e) {
            }
        }
    }
    @Test
    @DisplayName("Тестирование добавления нового вопроса")
    public void shouldAddNewQuestion() throws QuestionAlreadyExistsException, NoSuchFieldException, IllegalAccessException {
        Question newQuestion = new Question(Q_M_6, A_M_6);
        Question addedQuestion = repository.addQuestion(newQuestion);
        assertEquals(newQuestion, addedQuestion);

        Field questionsField = MathQuestionRepository.class.getDeclaredField("questions");
        questionsField.setAccessible(true);
        Map<String, String> internalQuestions = (Map<String, String>) questionsField.get(repository);
        assertTrue(internalQuestions.containsKey(newQuestion.getQuestion()));
        assertEquals(newQuestion.getAnswer(), internalQuestions.get(newQuestion.getQuestion()));
    }
    @Test
    @DisplayName("Тестирование дублирования вопроса")
    public void shouldThrowQuestionAlreadyExistsException() {
        assertThrows(QuestionAlreadyExistsException.class, () -> repository.addQuestion(Q_M_1, A_M_1));
    }
    @Test
    @DisplayName("Тестирование удаления вопроса")
    public void shouldRemoveQuestion() throws QuestionNotExistsException, NoSuchFieldException, IllegalAccessException {
        Question removedQuestion = repository.removeQuestion(MATH1);

        Field questionsField = MathQuestionRepository.class.getDeclaredField("questions");
        questionsField.setAccessible(true);
        Map<String, String> internalQuestions = (Map<String, String>) questionsField.get(repository);
        assertFalse(internalQuestions.containsKey(removedQuestion.getQuestion()));
    }
    @Test
    @DisplayName("Тестирование удаления несуществующего вопроса")
    public void shouldThrowQuestionNotExistsException() {
        assertThrows(QuestionNotExistsException.class, () -> repository.removeQuestion(MATH6));
    }
    @Test
    @DisplayName("Тестирование получения всех вопросов")
    public void shouldGetAllQuestions() {
        Collection<Question> allQuestions = repository.getAllQuestions();
        assertEquals(MATH_LIST.size(), allQuestions.size());
        assertTrue(allQuestions.contains(MATH1));
        assertTrue(allQuestions.contains(MATH2));
        assertTrue(allQuestions.contains(MATH3));
        assertTrue(allQuestions.contains(MATH4));
        assertTrue(allQuestions.contains(MATH5));
    }

}