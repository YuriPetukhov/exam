package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.repository.JavaQuestionRepository;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service("javaQuestionService")
public class JavaQuestionServiceImpl implements QuestionService {
    private final JavaQuestionRepository javaQuestionRepository;

    public JavaQuestionServiceImpl(JavaQuestionRepository javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }
    @Override
    public Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException {
        return javaQuestionRepository.addQuestion(question, answer);
    }

    @Override
    public Question addQuestion(Question question) throws QuestionAlreadyExistsException {
        return javaQuestionRepository.addQuestion(question);
    }
    @Override
    public Question removeQuestion(Question question) throws QuestionNotExistsException {
        return javaQuestionRepository.removeQuestion(question);
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = getAllQuestions();
        return allQuestions.stream()
                .skip(new Random().nextInt(allQuestions.size()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Question findQuestion(String question) {
        Optional<Question> foundQuestion = getAllQuestions().stream()
                .filter(existingQuestion -> existingQuestion.getQuestion().equals(question))
                .findFirst();
        return foundQuestion.orElse(null);
    }
    public Collection<Question> getAmountOfQuestions(int amount) throws NotValidNumberException, NotEnoughQuestionException {
        if (getAllQuestions().isEmpty()|| amount == 0) {
            return new ArrayList<>();
        }
        if (!NumberValidator.isPositiveNumber(amount)) {
            throw new NotValidNumberException();
        }

        List<Question> javaQuestions = new ArrayList<>(getAllQuestions());

        if (amount > javaQuestions.size()) {
            throw new NotEnoughQuestionException();
        }
        Set<Question> selectedQuestions = new HashSet<>();

        while (selectedQuestions.size() < amount) {
            Question randomQuestion = getRandomQuestion();
            selectedQuestions.add(randomQuestion);
        }
        return selectedQuestions;
    }
    @Override
    public List<Question> getAllQuestions() {
        return new ArrayList<>(javaQuestionRepository.getAllQuestions());
    }
}
