package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.exam.constant.QuestionsConstants.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService javaQuestionService;
    @Mock
    private QuestionService mathQuestionService;
    @InjectMocks
    private TestExaminerServiceImpl examinerService;

    @BeforeEach
    public void setUp() {
        examinerService = new TestExaminerServiceImpl(javaQuestionService, mathQuestionService);
    }

    @Test
    @DisplayName("Тест успешного получения вопросов")
    public void shouldGetQuestionsWhenEnoughQuestionsAvailable() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        // Arrange
        int amount = 10;

        when(javaQuestionService.getAllQuestions()).thenReturn(JAVA_LIST);
        when(mathQuestionService.getAllQuestions()).thenReturn(MATH_LIST);

        when(javaQuestionService.getAmountOfQuestions(5)).thenReturn(JAVA_LIST.subList(0, 5));
        when(mathQuestionService.getAmountOfQuestions(5)).thenReturn(MATH_LIST.subList(0, 5));


        // Act
        List<Question> resultQuestions = examinerService.getQuestions(amount);

        // Assert
        assertEquals(amount, resultQuestions.size());
        Set<Question> allQuestions = Stream.concat(JAVA_LIST.stream(), MATH_LIST.stream()).collect(Collectors.toSet());
        resultQuestions.forEach(question -> assertTrue(allQuestions.contains(question)));
    }

    @Test
    @DisplayName("Тест получения вопросов при большем количестве, чем доступно")
    public void shouldThrowExceptionNotEnoughQuestionsAvailable() {
        int amount = 16;
        when(javaQuestionService.getAllQuestions()).thenReturn(JAVA_LIST);
        when(mathQuestionService.getAllQuestions()).thenReturn(MATH_LIST);

        assertThrows(NotEnoughQuestionException.class, () -> {
            examinerService.getQuestions(amount);
        });
        verify(javaQuestionService, times(1)).getAllQuestions();
        verify(mathQuestionService, times(1)).getAllQuestions();
    }

    @Test
    @DisplayName("Тест получения вопросов, когда нет доступных вопросов")
    public void shouldThrowExceptionByEmptyList() {
        int amount = 5;

        assertThrows(NotEnoughQuestionException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }

    @Test
    @DisplayName("Тест получения вопросов при невалидном числе")
    public void shouldThrowExceptionNotValidNumber() {
        int amount = -2;
        assertThrows(NotValidNumberException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }
    private static class TestExaminerServiceImpl extends ExaminerServiceImpl {
        TestExaminerServiceImpl(QuestionService javaQuestionService, QuestionService mathQuestionService) {
            super(javaQuestionService, mathQuestionService);
        }

        @Override
        protected int getRandomNumberInRange(int min, int max) {
            if (min > max) {
                throw new IllegalArgumentException("max must be greater than min");
            }

            return 5;
        }
    }
}
