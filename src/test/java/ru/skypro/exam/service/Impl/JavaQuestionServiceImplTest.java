package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceImplTest {

    @Mock
    private JavaQuestionServiceImpl javaQuestionService;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        questions = Arrays.asList(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3"),
                new Question("Q4", "A4"),
                new Question("Q5", "A5")
        );
    }
    @Test
    @DisplayName("Тест добавления вопроса - успешный случай")
    void shouldAddJavaQuestion() throws QuestionAlreadyExistsException {
        javaQuestionService = new JavaQuestionServiceImpl();
        Question questionToAdd = questions.get(1);
        Question result = javaQuestionService.addJavaQuestion("Q2", "A2");
        assertEquals(questionToAdd, result);
        assertEquals(questionToAdd, javaQuestionService.findJavaQuestion("Q2"));
    }
    @Test
    @DisplayName("Тест добавления уже существующего вопроса")
    void shouldThrowExceptionWhenAddingExistingJavaQuestion() throws QuestionAlreadyExistsException {
        when(javaQuestionService.addJavaQuestion("Q3", "A3")).thenThrow(new QuestionAlreadyExistsException());

        assertThrows(QuestionAlreadyExistsException.class, () -> {
            javaQuestionService.addJavaQuestion("Q3", "A3");
        });

        verify(javaQuestionService, times(1)).addJavaQuestion("Q3", "A3");
    }
    @Test
    @DisplayName("Тест удаления вопроса")
    void shouldRemoveJavaQuestion() throws QuestionNotExistsException {
        Question questionToRemove = questions.get(0);
        when(javaQuestionService.removeJavaQuestion(questionToRemove)).thenReturn(questionToRemove);
        Question result = javaQuestionService.removeJavaQuestion(questionToRemove);
        assertEquals(questionToRemove, result);
        verify(javaQuestionService, times(1)).removeJavaQuestion(questionToRemove);
    }
    @Test
    @DisplayName("Тест удаления несуществующего вопроса")
    void shouldThrowExceptionWhenRemovingNonExistentJavaQuestion() throws QuestionNotExistsException {
        Question nonExistentQuestion = new Question("NonExistent", "Not Here");
        doThrow(new QuestionNotExistsException()).when(javaQuestionService).removeJavaQuestion(nonExistentQuestion);
        assertThrows(QuestionNotExistsException.class, () -> javaQuestionService.removeJavaQuestion(nonExistentQuestion));
        verify(javaQuestionService, times(1)).removeJavaQuestion(nonExistentQuestion);
    }
    @Test
    @DisplayName("Тест поиска вопроса")
    void shouldFindJavaQuestion() {
        Question questionToFind = questions.get(0);
        when(javaQuestionService.findJavaQuestion(questionToFind.getQuestion())).thenReturn(questionToFind);
        Question result = javaQuestionService.findJavaQuestion(questionToFind.getQuestion());
        assertEquals(questionToFind, result);
        verify(javaQuestionService, times(1)).findJavaQuestion(questionToFind.getQuestion());
    }
    @Test
    @DisplayName("Тест поиска несуществующего вопроса")
    void shouldThrowExceptionWhenFindingNonExistentJavaQuestion() {
        when(javaQuestionService.findJavaQuestion("NonExistentQuestion")).thenReturn(null);
        Question result = javaQuestionService.findJavaQuestion("NonExistentQuestion");
        assertNull(result);
        verify(javaQuestionService, times(1)).findJavaQuestion("NonExistentQuestion");
    }
    @Test
    @DisplayName("Тест получения случайного вопроса")
    void shouldGetRandomJavaQuestion() {
        Question questionToGet = questions.get(0);
        when(javaQuestionService.getRandomJavaQuestion()).thenReturn(questionToGet);
        Question result = javaQuestionService.getRandomJavaQuestion();
        assertEquals(questionToGet, result);
        verify(javaQuestionService, times(1)).getRandomJavaQuestion();
    }
    @Test
    @DisplayName("Тест получения вопросов")
    void shouldGetAmountOfJavaQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        when(javaQuestionService.getAmountOfJavaQuestions(2)).thenReturn(questions);
        Collection<Question> result = javaQuestionService.getAmountOfJavaQuestions(2);
        assertEquals(questions, result);
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(2);
    }
    @Test
    @DisplayName("Тест получения вопросов при большем количестве, чем доступно")
    void shouldThrowExceptionWhenGettingTooManyJavaQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        when(javaQuestionService.getAmountOfJavaQuestions(10)).thenThrow(new NotEnoughQuestionException());
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionService.getAmountOfJavaQuestions(10));
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(10);
    }
    @Test
    @DisplayName("Тест получения вопросов при невалидном числе")
    void shouldThrowExceptionWhenGettingAmountOfJavaQuestionsByNotValidNumber() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        doThrow(new NotValidNumberException()).when(javaQuestionService).getAmountOfJavaQuestions(-1);
        assertThrows(NotValidNumberException.class, () -> javaQuestionService.getAmountOfJavaQuestions(-1));
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(-1);
    }
    @Test
    @DisplayName("Тест получения всех вопросов")
    void shouldGetAllJavaQuestions() {
        when(javaQuestionService.getAllJavaQuestions()).thenReturn(questions);
        Collection<Question> result = javaQuestionService.getAllJavaQuestions();
        assertEquals(questions, result);
        verify(javaQuestionService, times(1)).getAllJavaQuestions();
    }
}