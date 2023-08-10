package ru.skypro.exam.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skypro.exam.constant.QuestionsConstants.*;

class MathQuestionRepositoryTest {
    private Set<Question> questions;
    private MathQuestionRepository repository;
    @BeforeEach
    public void setUp() {
        questions = new HashSet<>(MATH_LIST);
        repository = new MathQuestionRepository(questions);
    }

    @Test
    @DisplayName("Тестирование добавления нового вопроса")
    public void shouldAddNewQuestion() throws QuestionAlreadyExistsException {
        Question addedQuestion = repository.addQuestion(Q_M_6, A_M_6);
        assertTrue(questions.contains(addedQuestion));
    }

    @Test
    @DisplayName("Тестирование дублирования вопроса")
    public void shouldThrowQuestionAlreadyExistsException() {
        MathQuestionRepository repository = new MathQuestionRepository(questions);
        assertThrows(QuestionAlreadyExistsException.class, () -> repository.addQuestion(Q_M_1, A_M_1));
    }
    @Test
    @DisplayName("Тестирование удаления вопроса")
    public void shouldRemoveQuestion() throws QuestionNotExistsException {
        Question removedQuestion = repository.removeQuestion(MATH1);
        assertFalse(questions.contains(removedQuestion));
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