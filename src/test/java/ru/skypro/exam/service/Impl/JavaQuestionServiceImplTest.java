package ru.skypro.exam.service.Impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.exam.repository.JavaQuestionRepository;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.testData.TestData;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceImplTest {

    @InjectMocks
    private JavaQuestionServiceImpl javaQuestionServiceImpl;
    @Mock
    private JavaQuestionRepository javaQuestionRepository;
    private List<Question> allQuestions;
    private final int numberToRequest = 6;
    private final int tooManyQuestions = 10;
    private final int invalidAmount = -5;
    private final int totalAttempts = 100;

    @BeforeEach
    void init() {
        allQuestions = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            allQuestions.add(TestData.randomTestData());
        }
    }
    @AfterEach
    void clear() {
        allQuestions.clear();
        for (int i = 0; i < 7; i++) {
            allQuestions.add(TestData.randomTestData());
        }
    }
    @Test
    @DisplayName("Тестирование вызова методов репозитория при добавлении, удалении и получении всех вопросов")
    void shouldCallRepositoryMethodWhenAddRemoveAndGetAllQuestion() throws QuestionAlreadyExistsException, QuestionNotExistsException {
        Question question = TestData.randomTestData();
        List<Question> allQuestions = List.of(question);

        doReturn(question).when(javaQuestionRepository).addQuestion(any(Question.class));
        doReturn(question).when(javaQuestionRepository).removeQuestion(any(Question.class));
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);

        Question addedQuestion = javaQuestionServiceImpl.addQuestion(question.getQuestion(), question.getAnswer());
        Question removedQuestion = javaQuestionServiceImpl.removeQuestion(addedQuestion);
        assertEquals(allQuestions, javaQuestionServiceImpl.getAllQuestions());

        verify(javaQuestionRepository).addQuestion(argThat(q -> q.getQuestion().equals(question.getQuestion()) && q.getAnswer().equals(question.getAnswer())));
        verify(javaQuestionRepository).removeQuestion(argThat(q -> q.getQuestion().equals(question.getQuestion()) && q.getAnswer().equals(question.getAnswer())));
        verify(javaQuestionRepository).getAllQuestions();
    }

    @Test
    @DisplayName("Тестирование выбрасывания исключения при вызове методов репозитория")
    public void shouldThrowExceptionWhenRepositoryThrowsExceptions() throws QuestionAlreadyExistsException, QuestionNotExistsException {
        Question question = TestData.randomTestData();

        when(javaQuestionRepository.addQuestion(any())).thenThrow(QuestionAlreadyExistsException.class);
        when(javaQuestionRepository.removeQuestion(any())).thenThrow(QuestionNotExistsException.class);

        assertThrows(QuestionAlreadyExistsException.class, () -> javaQuestionServiceImpl.addQuestion(question.getQuestion(), question.getAnswer()));
        assertThrows(QuestionNotExistsException.class, () -> javaQuestionServiceImpl.removeQuestion(question));
    }
    @Test
    @DisplayName("Тестирование получения заданного количества вопросов")
    void shouldReturnCollectionOfQuestions() throws NotValidNumberException, NotEnoughQuestionException {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);
        Collection<Question> result = javaQuestionServiceImpl.getAmountOfQuestions(numberToRequest);

        assertEquals(numberToRequest, result.size());
    }
    @Test
    @DisplayName("Тестирование получения вопросов, когда список пустой")
    void shouldThrowExceptionByEmptyList() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(Collections.emptyList());
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(1));
    }
    @Test
    @DisplayName("Тестирование получения вопросов при большем количестве, чем доступно")
    void shouldThrowExceptionByNotEnoughQuestions() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(tooManyQuestions));
    }
    @Test
    @DisplayName("Тестирование получения вопросов при невалидном числе")
    void shouldThrowExceptionNotValidNumber() {
        assertThrows(NotValidNumberException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(invalidAmount));
    }
    @Test
    @DisplayName("Тестирование получения случайного вопроса из коллекции")
    void shouldGetRandomQuestion() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);
        Set<Question> uniqueQuestions = new HashSet<>();
        for (int i = 0; i < totalAttempts; i++) {
            uniqueQuestions.add(javaQuestionServiceImpl.getRandomQuestion());
        }
        allQuestions.forEach(question -> assertTrue(uniqueQuestions.contains(question)));
    }
}
