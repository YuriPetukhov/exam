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
    private QuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    @DisplayName("Тестирование получения вопросов - успешный случай")
    public void getQuestionsSuccessfulTest() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        int amount = 5;

        Collection<Question> allJavaQuestions = new ArrayList<>(Arrays.asList(
                new Question("Question 1", "Answer 1"),
                new Question("Question 2", "Answer 2"),
                new Question("Question 3", "Answer 3"),
                new Question("Question 4", "Answer 4"),
                new Question("Question 5", "Answer 5")
        ));

        when(javaQuestionService.getAllJavaQuestions()).thenReturn(allJavaQuestions);

        List<Question> randomQuestions = Arrays.asList(
                new Question("Question 1", "Answer 1"),
                new Question("Question 2", "Answer 2"),
                new Question("Question 3", "Answer 3"),
                new Question("Question 4", "Answer 4"),
                new Question("Question 5", "Answer 5")
        );
        when(javaQuestionService.getRandomJavaQuestion()).thenReturn(randomQuestions.get(0), randomQuestions.get(1), randomQuestions.get(2), randomQuestions.get(3), randomQuestions.get(4));

        Collection<Question> selectedQuestions = examinerService.getQuestions(amount);

        assertEquals(amount, selectedQuestions.size());
    }

    @Test
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    public void getQuestionsNotEnoughQuestionsAvailableTest() {
        int amount = 10;

        List<Question> javaQuestions = Arrays.asList(
                new Question("Question 1", "Answer 1"),
                new Question("Question 2", "Answer 2"),
                new Question("Question 3", "Answer 3")
        );
        when(javaQuestionService.getAllJavaQuestions()).thenReturn(javaQuestions);


        assertThrows(NotEnoughQuestionException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }

    @Test
    @DisplayName("Тестирование получения вопросов, когда нет доступных вопросов")
    public void testGetQuestions_NoQuestions() {

        int amount = 5;

        List<Question> javaQuestions = Collections.emptyList();
        when(javaQuestionService.getAllJavaQuestions()).thenReturn(javaQuestions);

        assertThrows(NotEnoughQuestionException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }

    @Test
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    public void getQuestionsNotValidNumberTest() {
        int amount = -2;
        assertThrows(NotValidNumberException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }
}