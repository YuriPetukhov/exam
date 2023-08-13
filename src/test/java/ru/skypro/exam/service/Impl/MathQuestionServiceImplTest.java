package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.repository.MathQuestionRepository;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static ru.skypro.exam.constant.QuestionsConstants.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceImplTest {

    @InjectMocks
    private MathQuestionServiceImpl mathQuestionServiceImpl;

    @Mock
    private MathQuestionRepository mathQuestionRepository;
    private List<Question> questions;
    @Test
    @DisplayName("Тестирование вызова методов репозитория при добавлении, удалении и получении всех вопросов")
    void shouldCallRepositoryMethodWhenAddRemoveAndGetAllQuestion() throws QuestionAlreadyExistsException, QuestionNotExistsException {
        Question question = new Question(Q_M_1, A_M_1);
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.add(question);

        when(mathQuestionRepository.addQuestion(Q_M_1, A_M_1))
                .thenReturn(question);
        when(mathQuestionRepository.removeQuestion(MATH1))
                .thenReturn(question);
        when(mathQuestionRepository.getAllQuestions())
                .thenReturn(allQuestions);

        assertEquals(question, mathQuestionServiceImpl.addQuestion(Q_M_1, A_M_1));
        assertEquals(question, mathQuestionServiceImpl.removeQuestion(MATH1));
        assertEquals(allQuestions, mathQuestionServiceImpl.getAllQuestions());

        verify(mathQuestionRepository, times(1)).addQuestion(Q_M_1, A_M_1);
        verify(mathQuestionRepository, times(1)).removeQuestion(MATH1);
        verify(mathQuestionRepository, times(1)).getAllQuestions();
    }

    @Test
    @DisplayName("Тестирование выбрасывания исключения при вызове методов репозитория")
    public void shouldThrowExceptionWhenRepositoryThrowsExceptions() throws QuestionNotExistsException, QuestionAlreadyExistsException {
        when(mathQuestionRepository.addQuestion(any(), any()))
                .thenThrow(QuestionAlreadyExistsException.class);
        when(mathQuestionRepository.removeQuestion(any()))
                .thenThrow(QuestionNotExistsException.class);

        assertThrows(QuestionAlreadyExistsException.class, () -> mathQuestionServiceImpl.addQuestion(Q_M_1, A_M_1));
        assertThrows(QuestionNotExistsException.class, () -> mathQuestionServiceImpl.removeQuestion(MATH1));
    }
    @Test
    @DisplayName("Тестирование получения заданного количества вопросов")
    void shouldReturnCollectionOfQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        int numberToRequest = 3;
        when(mathQuestionRepository.getAllQuestions()).thenReturn(MATH_LIST);
        Collection<Question> result = mathQuestionServiceImpl.getAmountOfQuestions(numberToRequest);

        assertEquals(numberToRequest, result.size());

        for (Question question : result) {
            assertTrue(MATH_LIST.contains(question));
        }
    }
    @Test
    @DisplayName("Тестирование получения вопросов, когда список пустой")
    void shouldThrowExceptionByEmptyListTest() {
        when(mathQuestionRepository.getAllQuestions()).thenReturn(new ArrayList<>());
        assertThrows(NotEnoughQuestionException.class, () -> mathQuestionServiceImpl.getAmountOfQuestions(1));
    }
    @Test
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    void shouldThrowExceptionByNotEnoughQuestions() {
        int tooManyQuestions = 10;
        when(mathQuestionRepository.getAllQuestions()).thenReturn(MATH_LIST);
        assertThrows(NotEnoughQuestionException.class, () -> mathQuestionServiceImpl.getAmountOfQuestions(tooManyQuestions));
    }
    @Test
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    void shouldThrowExceptionNotValidNumber() {
        int invalidAmount = -1;
        when(mathQuestionRepository.getAllQuestions()).thenReturn(MATH_LIST);
        assertThrows(NotValidNumberException.class, () -> mathQuestionServiceImpl.getAmountOfQuestions(invalidAmount));
    }
    @Test
    @DisplayName("Тестирование получения случайного вопроса из коллекции")
    void shouldGetRandomQuestion() {
        when(mathQuestionRepository.getAllQuestions()).thenReturn(MATH_LIST);

        Set<Question> uniqueQuestions = new HashSet<>();
        int totalAttempts = 100;

        for (int i = 0; i < totalAttempts; i++) {
            uniqueQuestions.add(mathQuestionServiceImpl.getRandomQuestion());
        }
        assertTrue(uniqueQuestions.contains(MATH1));
        assertTrue(uniqueQuestions.contains(MATH2));
        assertTrue(uniqueQuestions.contains(MATH3));
        assertTrue(uniqueQuestions.contains(MATH4));
        assertTrue(uniqueQuestions.contains(MATH5));
    }
}