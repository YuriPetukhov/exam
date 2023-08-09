package ru.skypro.exam.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.testData.TestData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestData.class)
class JavaQuestionRepositoryTest {
    private Set<Question> questions;
    private JavaQuestionRepository repository;

    @BeforeEach
    public void setUp() {
        questions = new HashSet<>();
        for (int i = 0; i < 15; i++) {
            Question question = TestData.randomTestData();
            questions.add(question);
        }
        repository = new JavaQuestionRepository();

        for (Question question : questions) {
            try {
                repository.addQuestion(question);
            } catch (QuestionAlreadyExistsException ignored) {
            }
        }
    }
    @Test
    @DisplayName("Тестирование добавления нового вопроса")
    public void shouldAddNewQuestion() throws QuestionAlreadyExistsException {
        Question newQuestion = TestData.randomTestData();
        Question addedQuestion = repository.addQuestion(newQuestion);
        assertEquals(addedQuestion, repository.getQuestions().get(addedQuestion.getId()));
    }

    @Test
    @DisplayName("Тестирование дублирования вопроса")
    public void shouldThrowQuestionAlreadyExistsException() {
        Question existingQuestion = questions.iterator().next();
        assertThrows(QuestionAlreadyExistsException.class, () -> repository.addQuestion(existingQuestion));
    }

    @Test
    @DisplayName("Тестирование удаления вопроса")
    public void shouldRemoveQuestion() throws QuestionNotExistsException {
        Question sampleQuestion = questions.iterator().next();
        Question removedQuestion = repository.removeQuestion(sampleQuestion);
        assertFalse(repository.getAllQuestions().contains(removedQuestion));
    }

    @Test
    @DisplayName("Тестирование удаления несуществующего вопроса")
    public void shouldThrowQuestionNotExistsException() {
        Question nonExistingQuestion = TestData.randomNonExistingQuestion();
        assertThrows(QuestionNotExistsException.class, () -> repository.removeQuestion(nonExistingQuestion));
    }

    @Test
    @DisplayName("Тестирование получения всех вопросов")
    public void shouldGetAllQuestions() {
        Collection<Question> allQuestions = repository.getAllQuestions();
        assertEquals(questions.size(), allQuestions.size());
        assertTrue(allQuestions.containsAll(questions));
    }
}
