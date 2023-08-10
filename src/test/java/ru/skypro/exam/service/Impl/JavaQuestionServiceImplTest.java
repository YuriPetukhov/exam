package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.Repository.JavaQuestionRepository;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static ru.skypro.exam.constant.QuestionsConstants.*;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceImplTest {

    @InjectMocks
    private JavaQuestionServiceImpl javaQuestionServiceImpl;

    @Mock
    private JavaQuestionRepository javaQuestionRepository;
    private List<Question> questions;
    @Test
    @DisplayName("Тестирование вызова методов репозитория при добавлении, удалении и получении всех вопросов")
    void shouldCallRepositoryMethodWhenAddRemoveAndGetAllQuestion() throws QuestionAlreadyExistsException, QuestionNotExistsException {
        Question question = new Question(Q_J_1, A_J_1);
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.add(question);

        when(javaQuestionRepository.addQuestion(Q_J_1, A_J_1))
                .thenReturn(question);
        when(javaQuestionRepository.removeQuestion(JAVA1))
                .thenReturn(question);
        when(javaQuestionRepository.getAllQuestions())
                .thenReturn(allQuestions);

        assertEquals(question, javaQuestionServiceImpl.addQuestion(Q_J_1, A_J_1));
        assertEquals(question, javaQuestionServiceImpl.removeQuestion(JAVA1));
        assertEquals(allQuestions, javaQuestionServiceImpl.getAllQuestions());

        verify(javaQuestionRepository, times(1)).addQuestion(Q_J_1, A_J_1);
        verify(javaQuestionRepository, times(1)).removeQuestion(JAVA1);
        verify(javaQuestionRepository, times(1)).getAllQuestions();
    }

    @Test
    @DisplayName("Тестирование выбрасывания исключения при вызове методов репозитория")
    public void shouldThrowExceptionWhenRepositoryThrowsExceptions() throws QuestionNotExistsException, QuestionAlreadyExistsException {
        when(javaQuestionRepository.addQuestion(any(), any()))
                .thenThrow(QuestionAlreadyExistsException.class);
        when(javaQuestionRepository.removeQuestion(any()))
                .thenThrow(QuestionNotExistsException.class);

        assertThrows(QuestionAlreadyExistsException.class, () -> javaQuestionServiceImpl.addQuestion(Q_J_1, A_J_1));
        assertThrows(QuestionNotExistsException.class, () -> javaQuestionServiceImpl.removeQuestion(JAVA1));
    }
    @Test
    @DisplayName("Тестирование получения заданного количества вопросов")
    void shouldReturnCollectionOfQuestions() throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        int numberToRequest = 3;
        when(javaQuestionRepository.getAllQuestions()).thenReturn(JAVA_LIST);
        Collection<Question> result = javaQuestionServiceImpl.getAmountOfQuestions(numberToRequest);

        assertEquals(numberToRequest, result.size());

        for (Question question : result) {
            assertTrue(JAVA_LIST.contains(question));
        }
    }
    @Test
    @DisplayName("Тестирование получения вопросов, когда список пустой")
    void shouldThrowExceptionByEmptyListTest() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(new ArrayList<>());
        assertThrows(QuestionNotExistsException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(1));
    }
    @Test
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    void shouldThrowExceptionByNotEnoughQuestions() {
        int tooManyQuestions = 10;
        when(javaQuestionRepository.getAllQuestions()).thenReturn(JAVA_LIST);
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(tooManyQuestions));
    }
    @Test
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    void shouldThrowExceptionNotValidNumber() {
        int invalidAmount = -1;
        when(javaQuestionRepository.getAllQuestions()).thenReturn(JAVA_LIST);
        assertThrows(NotValidNumberException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(invalidAmount));
    }
    @Test
    @DisplayName("Тестирование получения случайного вопроса из коллекции")
    void shouldGetRandomQuestion() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(JAVA_LIST);

        Set<Question> uniqueQuestions = new HashSet<>();
        int totalAttempts = 100;

        for (int i = 0; i < totalAttempts; i++) {
            uniqueQuestions.add(javaQuestionServiceImpl.getRandomQuestion());
        }
        assertTrue(uniqueQuestions.contains(JAVA1));
        assertTrue(uniqueQuestions.contains(JAVA2));
        assertTrue(uniqueQuestions.contains(JAVA3));
        assertTrue(uniqueQuestions.contains(JAVA4));
        assertTrue(uniqueQuestions.contains(JAVA5));
    }
}