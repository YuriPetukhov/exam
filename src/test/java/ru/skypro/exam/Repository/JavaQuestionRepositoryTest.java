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

class JavaQuestionRepositoryTest {
    private Set<Question> questions;
    private JavaQuestionRepository repository;

    @BeforeEach
    public void setUp() {
        questions = new HashSet<>(JAVA_LIST);
        repository = new JavaQuestionRepository();
    }

    @Test
    @DisplayName("Тестирование добавления нового вопроса")
    public void shouldAddNewQuestion() throws QuestionAlreadyExistsException {
        Question addedQuestion = repository.addQuestion(Q_J_6, A_J_6);
        assertTrue(questions.contains(addedQuestion));
    }

    @Test
    @DisplayName("Тестирование дублирования вопроса")
    public void shouldThrowQuestionAlreadyExistsException() {
        assertThrows(QuestionAlreadyExistsException.class, () -> repository.addQuestion(Q_J_1, A_J_1));
    }
    @Test
    @DisplayName("Тестирование удаления вопроса")
    public void shouldRemoveQuestion() throws QuestionNotExistsException {
        Question removedQuestion = repository.removeQuestion(JAVA1);
        assertFalse(questions.contains(removedQuestion));
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