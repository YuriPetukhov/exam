package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.testData.TestData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService javaQuestionService;
    @Mock
    private QuestionService mathQuestionService;
    @InjectMocks
    private ExaminerServiceImpl examinerService;
    private final int invalidAmount = -2;

    @BeforeEach
    public void setUp() {
        Set<QuestionService> questionServices = new HashSet<>();
        questionServices.add(javaQuestionService);
        questionServices.add(mathQuestionService);
        examinerService = new ExaminerServiceImpl(questionServices);
    }
    @Test
    @DisplayName("Тестирование успешного получения вопросов")
    public void shouldGetQuestionsWhenEnoughQuestionsAvailable() throws NotValidNumberException, NotEnoughQuestionException, MethodNotAllowedException {
        int amount = 6;
        List<Question> javaQuestions = Stream.generate(TestData::randomTestData)
                .limit(5)
                .collect(Collectors.toList());
        List<Question> mathQuestions = Stream.generate(TestData::randomTestData)
                .limit(5)
                .collect(Collectors.toList());
        when(javaQuestionService.getAllQuestions()).thenReturn(javaQuestions);
        when(mathQuestionService.getAllQuestions()).thenReturn(mathQuestions);

        List<Question> resultQuestions = examinerService.getQuestions(amount);

        verify(javaQuestionService, times(1)).getAllQuestions();
        verify(mathQuestionService, times(1)).getAllQuestions();

        assertEquals(amount, resultQuestions.size());

        Set<Question> allQuestions = Stream.concat(javaQuestions.stream(), mathQuestions.stream()).collect(Collectors.toSet());
        resultQuestions.forEach(question -> assertTrue(allQuestions.contains(question)));
    }

    @Test
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    public void shouldThrowExceptionNotEnoughQuestions() throws MethodNotAllowedException {
        int amount = 16;
        List<Question> javaQuestions = Stream.generate(TestData::randomTestData)
                .limit(5)
                .collect(Collectors.toList());
        List<Question> mathQuestions = Stream.generate(TestData::randomTestData)
                .limit(5)
                .collect(Collectors.toList());
        when(javaQuestionService.getAllQuestions()).thenReturn(javaQuestions);
        when(mathQuestionService.getAllQuestions()).thenReturn(mathQuestions);

        assertThrows(NotEnoughQuestionException.class, () -> {
            examinerService.getQuestions(amount);
        });
        verify(javaQuestionService, times(1)).getAllQuestions();
        verify(mathQuestionService, times(1)).getAllQuestions();
    }

    @Test
    @DisplayName("Тестирование получения вопросов, когда список пустой")
    public void shouldThrowExceptionByEmptyList() throws MethodNotAllowedException {
        int amount = 15;
        List<Question> questions = new ArrayList<>();
        when(javaQuestionService.getAllQuestions()).thenReturn(questions);
        when(mathQuestionService.getAllQuestions()).thenReturn(questions);
        assertThrows(NotEnoughQuestionException.class, () -> {
            examinerService.getQuestions(amount);
        });
    }
    @Test
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    void shouldThrowExceptionNotValidNumber() {
        assertThrows(NotValidNumberException.class, () ->
                examinerService.getQuestions(invalidAmount));
    }
}
