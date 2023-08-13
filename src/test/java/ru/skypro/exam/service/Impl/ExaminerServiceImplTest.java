package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    @DisplayName("Тест получения вопросов - успешный случай")
    public void shouldGetQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        int amount = 5;

        Collection<Question> allJavaQuestions = new ArrayList<>(Arrays.asList(
                new Question("Question 1", "Answer 1"),
                new Question("Question 2", "Answer 2"),
                new Question("Question 3", "Answer 3"),
                new Question("Question 4", "Answer 4"),
                new Question("Question 5", "Answer 5")
        ));

        when(questionService.getAmountOfJavaQuestions(amount)).thenReturn(allJavaQuestions);

        Collection<Question> selectedQuestions = examinerService.getQuestions(amount);

        assertEquals(amount, selectedQuestions.size());
    }

    @Test
    @DisplayName("Тест получения вопросов при большем количестве, чем доступно")
    public void shouldThrowExceptionWhenGettingToManyQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        int amount = 10;

        when(questionService.getAmountOfJavaQuestions(amount)).thenThrow(NotEnoughQuestionException.class);
        assertThrows(NotEnoughQuestionException.class, () -> {examinerService.getQuestions(amount);
        });
    }

    @Test
    @DisplayName("Тест получения вопросов, когда нет доступных вопросов")
    public void shouldThrowExceptionWhenNotAvailableQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        int amount = 15;

        when(questionService.getAmountOfJavaQuestions(amount)).thenThrow(NotEnoughQuestionException.class);
        assertThrows(NotEnoughQuestionException.class, () -> examinerService.getQuestions(amount));
    }

    @Test
    @DisplayName("Тест получения вопросов при невалидном числе")
    public void shouldThrowExceptionWhenGettingAmountOfJavaQuestionsByNotValidNumber() {
        int amount = -2;
        assertThrows(NotValidNumberException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }
}
