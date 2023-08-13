package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.testData.TestData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceImplTest {
    @InjectMocks
    MathQuestionServiceImpl mathQuestionService;

    private final int numberToRequest = 6;
    private final int invalidAmount = -1;
    private final int totalAttempts = 100;
    private MathQuestionServiceImpl mathQuestionServiceMock;
    @BeforeEach
    void setUp() {
        mathQuestionServiceMock = spy(mathQuestionService);
    }
    @Test
    @DisplayName("Тест вызова методов репозитория при добавлении, удалении, поиске, запросе количества и получении всех вопросов")
    void shouldThrowExceptionWhenQuestionAlreadyExists() throws MethodNotAllowedException {
        Question question = TestData.randomTestData();

        doThrow(MethodNotAllowedException.class).when(mathQuestionServiceMock).addQuestion(question.getQuestion(), question.getAnswer());
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionServiceMock.addQuestion(question.getQuestion(), question.getAnswer()));
        verify(mathQuestionServiceMock, times(1)).addQuestion(question.getQuestion(), question.getAnswer());

        doThrow(MethodNotAllowedException.class).when(mathQuestionServiceMock).removeQuestion(any());
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionServiceMock.removeQuestion(question));
        verify(mathQuestionServiceMock, times(1)).removeQuestion(question);

        doThrow(MethodNotAllowedException.class).when(mathQuestionServiceMock).findQuestion(question.getQuestion());
        assertThrows(MethodNotAllowedException.class, () -> mathQuestionServiceMock.findQuestion(question.getQuestion()));
        verify(mathQuestionServiceMock, times(1)).findQuestion(question.getQuestion());

        doThrow(MethodNotAllowedException.class).when(mathQuestionServiceMock).getAllQuestions();
        assertThrows(MethodNotAllowedException.class, mathQuestionServiceMock::getAllQuestions);
        verify(mathQuestionServiceMock, times(1)).getAllQuestions();
    }
    @Test
    @DisplayName("Тест вызова и генерации случайных математических вопросов")
    void shouldGenerateRandomMathQuestions() {
        Set<Question> uniqueQuestions = new HashSet<>();

        for (int i = 0; i < totalAttempts; i++) {
            Question randomQuestion = mathQuestionService.getRandomQuestion();
            assertNotNull(randomQuestion);
            assertNotNull(randomQuestion.getQuestion());
            assertNotNull(randomQuestion.getAnswer());
            uniqueQuestions.add(randomQuestion);
        }
        assertTrue(uniqueQuestions.size() > 1, "Сгенерированные вопросы должны отличаться");
    }
    @Test
    @DisplayName("Тест получения заданного количества вопросов")
    void shouldReturnCollectionOfQuestions() throws NotValidNumberException {
        Collection<Question> result = mathQuestionService.getAmountOfQuestions(numberToRequest);
        assertEquals(numberToRequest, result.size());
    }
    @Test
    @DisplayName("Тест получения вопросов при невалидном числе")
    void shouldThrowExceptionNotValidNumber() {
        assertThrows(NotValidNumberException.class, () -> mathQuestionService.getAmountOfQuestions(invalidAmount));
    }
}
