package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.testData.TestData;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceImplTest {

    @InjectMocks
    MathQuestionServiceImpl mathQuestionService;

    @Test
    @DisplayName("Тестирование вызова методов репозитория при добавлении, удалении, поиске, запросе количества и получении всех вопросов")
    void shouldCallRepositoryMethodsAndThrowMethodNotAllowedException() throws MethodNotAllowedException {
        MathQuestionServiceImpl mathQuestionService = Mockito.spy(new MathQuestionServiceImpl());
        Question question = TestData.randomTestData();

        doThrow(MethodNotAllowedException.class).when(mathQuestionService).addQuestion(question.getQuestion(), question.getAnswer());
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionService.addQuestion(question.getQuestion(), question.getAnswer()));
        verify(mathQuestionService, times(1)).addQuestion(question.getQuestion(), question.getAnswer());

        doThrow(MethodNotAllowedException.class).when(mathQuestionService).removeQuestion(any());
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionService.removeQuestion(question));
        verify(mathQuestionService, times(1)).removeQuestion(question);

        doThrow(MethodNotAllowedException.class).when(mathQuestionService).findQuestion(question.getQuestion());
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionService.findQuestion(question.getQuestion()));
        verify(mathQuestionService, times(1)).findQuestion(question.getQuestion());

        doThrow(MethodNotAllowedException.class).when(mathQuestionService).getAmountOfQuestions(5);
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionService.getAmountOfQuestions(5));
        verify(mathQuestionService, times(1)).getAmountOfQuestions(5);

        doThrow(MethodNotAllowedException.class).when(mathQuestionService).getAllQuestions();
        assertThrows(MethodNotAllowedException.class, mathQuestionService::getAllQuestions);
        verify(mathQuestionService, times(1)).getAllQuestions();
    }


    @Test
    @DisplayName("Тестирование вызова и генерации случайных математических вопросов")
    void shouldGenerateRandomMathQuestions() {
        Set<Question> uniqueQuestions = new HashSet<>();
        int totalAttempts = 100;

        for (int i = 0; i < totalAttempts; i++) {
            Question randomQuestion = mathQuestionService.getRandomQuestion();
            assertNotNull(randomQuestion);
            assertNotNull(randomQuestion.getQuestion());
            assertNotNull(randomQuestion.getAnswer());
            uniqueQuestions.add(randomQuestion);
        }

        assertTrue(uniqueQuestions.size() > 1, "Сгенерированные вопросы должны отличаться");
    }
}
