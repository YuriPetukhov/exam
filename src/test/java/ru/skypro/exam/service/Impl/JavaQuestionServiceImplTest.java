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
    @DisplayName("Тестирование добавления вопроса - успешный случай")
    void addJavaQuestionSuccessfulTest() throws QuestionAlreadyExistsException {
        javaQuestionService = new JavaQuestionServiceImpl();
        Question questionToAdd = questions.get(1);
        Question result = javaQuestionService.addJavaQuestion("Q2", "A2");
        assertEquals(questionToAdd, result);
        assertEquals(questionToAdd, javaQuestionService.findJavaQuestion("Q2"));
    }
    @Test
    @DisplayName("Тестирование добавления уже существующего вопроса - QuestionAlreadyExistsException")
    void addJavaQuestionQuestionExistsTest() throws QuestionAlreadyExistsException {
        when(javaQuestionService.addJavaQuestion("Q3", "A3")).thenThrow(new QuestionAlreadyExistsException());
        assertThrows(QuestionAlreadyExistsException.class, () -> {
            javaQuestionService.addJavaQuestion("Q3", "A3");
        });
        verify(javaQuestionService, times(1)).addJavaQuestion("Q3", "A3");
    }
    @Test
    @DisplayName("Тестирование удаления вопроса")
    void removeJavaQuestionTest() throws QuestionNotExistsException {
        Question questionToRemove = questions.get(0);
        when(javaQuestionService.removeJavaQuestion(questionToRemove)).thenReturn(questionToRemove);
        Question result = javaQuestionService.removeJavaQuestion(questionToRemove);
        assertEquals(questionToRemove, result);
        verify(javaQuestionService, times(1)).removeJavaQuestion(questionToRemove);
    }
    @Test
    @DisplayName("Тестирование поиска вопроса")
    void findJavaQuestionTest() {
        Question questionToFind = questions.get(0);
        when(javaQuestionService.findJavaQuestion(questionToFind.getQuestion())).thenReturn(questionToFind);
        Question result = javaQuestionService.findJavaQuestion(questionToFind.getQuestion());
        assertEquals(questionToFind, result);
        verify(javaQuestionService, times(1)).findJavaQuestion(questionToFind.getQuestion());
    }
    @Test
    @DisplayName("Тестирование получения случайного вопроса")
    void getRandomJavaQuestionTest() {
        Question questionToGet = questions.get(0);
        when(javaQuestionService.getRandomJavaQuestion()).thenReturn(questionToGet);
        Question result = javaQuestionService.getRandomJavaQuestion();
        assertEquals(questionToGet, result);
        verify(javaQuestionService, times(1)).getRandomJavaQuestion();
    }
    @Test
    @DisplayName("Тестирование получения вопросов")
    void getAmountOfJavaQuestionSuccessfulTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        when(javaQuestionService.getAmountOfJavaQuestions(2)).thenReturn(questions);
        Collection<Question> result = javaQuestionService.getAmountOfJavaQuestions(2);
        assertEquals(questions, result);
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(2);
    }
    @Test
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    void getAmountOfJavaQuestionByNotEnoughNumberTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        when(javaQuestionService.getAmountOfJavaQuestions(10)).thenThrow(new NotEnoughQuestionException());
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionService.getAmountOfJavaQuestions(10));
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(10);
    }
    @Test
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    void getAmountOfJavaQuestionByNotValidNumberTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        doThrow(new NotValidNumberException()).when(javaQuestionService).getAmountOfJavaQuestions(-1);
        assertThrows(NotValidNumberException.class, () -> javaQuestionService.getAmountOfJavaQuestions(-1));
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(-1);
    }
    @Test
    @DisplayName("Тестирование получения всех вопросов")
    void getAllJavaQuestionsTest() {
        when(javaQuestionService.getAllJavaQuestions()).thenReturn(questions);
        Collection<Question> result = javaQuestionService.getAllJavaQuestions();
        assertEquals(questions, result);
        verify(javaQuestionService, times(1)).getAllJavaQuestions();
    }
}