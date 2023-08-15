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
    private static final int NUMBER_OF_QUESTIONS_TO_REQUEST = 6;
    private static final int TOO_MANY_QUESTIONS = 10;
    private static final int INVALID_AMOUNT = -5;
    private static final int TOTAL_ATTEMPTS = 100;

    @BeforeEach
    void init() {
        allQuestions = TestData.generateRandomTestData(7);
    }

    @Test
    @DisplayName("Тест вызова методов репозитория при добавлении, удалении и получении всех вопросов")
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
    @DisplayName("Тест выброса исключения при добавлении и удалении вопросов")
    public void shouldThrowExceptionWhenAddingAndRemovingQuestions() throws QuestionAlreadyExistsException, QuestionNotExistsException {
        Question question = TestData.randomTestData();

        when(javaQuestionRepository.addQuestion(any())).thenThrow(QuestionAlreadyExistsException.class);
        when(javaQuestionRepository.removeQuestion(any())).thenThrow(QuestionNotExistsException.class);

        assertThrows(QuestionAlreadyExistsException.class, () -> javaQuestionServiceImpl.addQuestion(question.getQuestion(), question.getAnswer()));
        assertThrows(QuestionNotExistsException.class, () -> javaQuestionServiceImpl.removeQuestion(question));
    }
    @Test
    @DisplayName("Тест получения заданного количества вопросов")
    void shouldReturnCollectionOfQuestions() throws NotValidNumberException, NotEnoughQuestionException {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(new ArrayList<>(allQuestions));
        Collection<Question> result = javaQuestionServiceImpl.getAmountOfQuestions(NUMBER_OF_QUESTIONS_TO_REQUEST);
        assertEquals(NUMBER_OF_QUESTIONS_TO_REQUEST, result.size());
    }
    @Test
    @DisplayName("Тест получения вопросов при пустом списке")
    void shouldReturnEmptyListWhenListIsEmpty() throws NotValidNumberException, NotEnoughQuestionException {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(new ArrayList<>());
        assertTrue(javaQuestionServiceImpl.getAmountOfQuestions(1).isEmpty());
    }
    @Test
    @DisplayName("Тест получения вопросов при большем количестве, чем доступно")
    void shouldThrowExceptionByNotEnoughQuestions() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);
        assertThrows(NotEnoughQuestionException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(TOO_MANY_QUESTIONS));
    }
    @Test
    @DisplayName("Тест получения вопросов при невалидном числе")
    void shouldThrowExceptionNotValidNumber() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);
        assertThrows(NotValidNumberException.class, () -> javaQuestionServiceImpl.getAmountOfQuestions(INVALID_AMOUNT));
    }
    @Test
    @DisplayName("Тест получения случайного вопроса из коллекции")
    void shouldGetRandomQuestion() {
        when(javaQuestionRepository.getAllQuestions()).thenReturn(allQuestions);
        Set<Question> uniqueQuestions = new HashSet<>();
        for (int i = 0; i < TOTAL_ATTEMPTS; i++) {
            uniqueQuestions.add(javaQuestionServiceImpl.getRandomQuestion());
        }
        allQuestions.forEach(question -> assertTrue(uniqueQuestions.contains(question)));
    }
}
