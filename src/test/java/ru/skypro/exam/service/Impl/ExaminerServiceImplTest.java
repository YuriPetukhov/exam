package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionServiceImpl javaQuestionService;
    @Mock
    private MathQuestionServiceImpl mathQuestionService;
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void setUp() {
        Set<QuestionService> questionServices = new HashSet<>();
        questionServices.add(javaQuestionService);
        questionServices.add(mathQuestionService);
        examinerService = new ExaminerServiceImpl(questionServices, 100);
    }
    @Test
    @DisplayName("Тест успешного получения вопросов")
    public void shouldGetQuestionsWhenEnoughQuestionsAvailable() throws NotEnoughQuestionException, NotValidNumberException {
        int amountOfQuestions = 6;

        registerServices(examinerService, javaQuestionService, mathQuestionService);

        when(javaQuestionService.getAmountOfQuestions(anyInt())).thenReturn(Collections.nCopies(4, new Question("JavaQuestion", "JavaAnswer")));
        when(mathQuestionService.getAmountOfQuestions(anyInt())).thenReturn(Collections.nCopies(2, new Question("MathQuestion", "MathAnswer")));

        List<Question> resultQuestions = examinerService.getQuestions(amountOfQuestions);

        assertEquals(amountOfQuestions, resultQuestions.size());
        verify(javaQuestionService, atLeastOnce()).getAmountOfQuestions(anyInt());
        verify(mathQuestionService, atLeastOnce()).getAmountOfQuestions(anyInt());
    }
    @Test
    @DisplayName("Тест выброса исключения NotValidNumberException")
    public void shouldThrowNotValidNumberExceptionWhenInvalidNumber() {
        int invalidAmount = -1;

        assertThrows(NotValidNumberException.class,
                () -> examinerService.getQuestions(invalidAmount));
    }
    @Test
    @DisplayName("Тест выброса исключения NotEnoughQuestionException")
    public void shouldThrowNotEnoughQuestionExceptionWhenNotEnoughQuestions() {
        int tooManyQuestions = 101;
        registerServices(examinerService, javaQuestionService, mathQuestionService);

        assertThrows(NotEnoughQuestionException.class,
                () -> examinerService.getQuestions(tooManyQuestions));
    }
    private void registerServices(ExaminerService examinerService, JavaQuestionServiceImpl javaQuestionService, MathQuestionServiceImpl mathQuestionService) {
        Map<Class<? extends QuestionService>, QuestionService> questionServicesMap = new HashMap<>();
        questionServicesMap.put(JavaQuestionServiceImpl.class, javaQuestionService);
        questionServicesMap.put(MathQuestionServiceImpl.class, mathQuestionService);

        Field field;
        try {
            field = examinerService.getClass().getDeclaredField("questionServicesMap");
            field.setAccessible(true);
            field.set(examinerService, questionServicesMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to register services in questionServicesMap");
        }
    }
}
