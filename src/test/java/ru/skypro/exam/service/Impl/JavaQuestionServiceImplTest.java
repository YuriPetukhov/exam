package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceImplTest {

    @Mock
    private QuestionService javaQuestionService;
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

    @DisplayName("Тестирование добавления вопроса - успешный случай")
    @Test
    void addJavaQuestionSuccessfulTest() throws AnswerAlreadyExistsException, QuestionAlreadyExistsException {
        Question questionToAdd = questions.get(1);
        when(javaQuestionService.addJavaQuestion("Q2", "A2")).thenReturn(questionToAdd);
        Question result = javaQuestionService.addJavaQuestion("Q2", "A2");
        assertEquals(questionToAdd, result);
        verify(javaQuestionService, times(1)).addJavaQuestion("Q2", "A2");
    }
    @DisplayName("Тестирование добавления уже существующего вопроса - QuestionAlreadyExistsException")
    @Test
    void addJavaQuestionQuestionExistsTest() throws AnswerAlreadyExistsException, QuestionAlreadyExistsException {
        when(javaQuestionService.addJavaQuestion("Q3", "A3")).thenThrow(new QuestionAlreadyExistsException());
        assertThrows(QuestionAlreadyExistsException.class, () -> {
            javaQuestionService.addJavaQuestion("Q3", "A3");
        });
        verify(javaQuestionService, times(1)).addJavaQuestion("Q3", "A3");
    }
    @DisplayName("Тестирование добавления вопроса, когда уже есть вопрос с таким же ответом - AnswerAlreadyExistsException")
    @Test
    void addJavaQuestionDuplicateAnswerTest() throws AnswerAlreadyExistsException, QuestionAlreadyExistsException {
        when(javaQuestionService.addJavaQuestion("Q6", "A3")).thenThrow(new AnswerAlreadyExistsException());
        assertThrows(AnswerAlreadyExistsException.class, () -> {
            javaQuestionService.addJavaQuestion("Q6", "A3");
        });
        verify(javaQuestionService, times(1)).addJavaQuestion("Q6", "A3");
    }
    @DisplayName("Тестирование удаления вопроса")
    @Test
    void removeJavaQuestionTest() throws QuestionNotExistsException {
        Question questionToRemove = questions.get(0);
        when(javaQuestionService.removeJavaQuestion(questionToRemove.getQuestion())).thenReturn(questionToRemove);
        Question result = javaQuestionService.removeJavaQuestion(questionToRemove.getQuestion());
        assertEquals(questionToRemove, result);
        verify(javaQuestionService, times(1)).removeJavaQuestion(questionToRemove.getQuestion());
    }

    @DisplayName("Тестирование поиска вопроса")
    @Test
    void findJavaQuestionTest() throws QuestionNotExistsException {
        Question questionToFind = questions.get(0);
        when(javaQuestionService.findJavaQuestion(questionToFind.getQuestion())).thenReturn(questionToFind);
        Question result = javaQuestionService.findJavaQuestion(questionToFind.getQuestion());
        assertEquals(questionToFind, result);
        verify(javaQuestionService, times(1)).findJavaQuestion(questionToFind.getQuestion());
    }

    @DisplayName("Тестирование получения случайного вопроса")
    @Test
    void getRandomJavaQuestionTest() {
        Question questionToGet = questions.get(0);
        when(javaQuestionService.getRandomJavaQuestion()).thenReturn(questionToGet);
        Question result = javaQuestionService.getRandomJavaQuestion();
        assertEquals(questionToGet, result);
        verify(javaQuestionService, times(1)).getRandomJavaQuestion();
    }
    @DisplayName("Тестирование получения вопросов")
    @Test
    void getAmountOfJavaQuestionSuccessfulTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        when(javaQuestionService.getAmountOfJavaQuestions(2)).thenReturn(questions);
        Collection<Question> result = javaQuestionService.getAmountOfJavaQuestions(2);
        assertEquals(questions, result);
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(2);
    }
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    @Test
    void getAmountOfJavaQuestionByNotEnoughNumberTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        when(javaQuestionService.getAmountOfJavaQuestions(10)).thenThrow(new NotEnoughQuestionException());
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionService.getAmountOfJavaQuestions(10));
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(10);
    }
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    @Test
    void getAmountOfJavaQuestionByNotValidNumberTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        doThrow(new NotValidNumberException()).when(javaQuestionService).getAmountOfJavaQuestions(-1);
        assertThrows(NotValidNumberException.class, () -> javaQuestionService.getAmountOfJavaQuestions(-1));
        verify(javaQuestionService, times(1)).getAmountOfJavaQuestions(-1);
    }
    @DisplayName("Тестирование получения всех вопросов")
    @Test
    void getAllJavaQuestionsTest() {
        when(javaQuestionService.getAllJavaQuestions()).thenReturn(questions);
        Collection<Question> result = javaQuestionService.getAllJavaQuestions();
        assertEquals(questions, result);
        verify(javaQuestionService, times(1)).getAllJavaQuestions();
    }
}