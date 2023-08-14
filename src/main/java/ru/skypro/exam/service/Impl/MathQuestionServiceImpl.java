package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.repository.MathQuestionRepository;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service("mathQuestionService")
public class MathQuestionServiceImpl implements QuestionService {
    private final MathQuestionRepository mathQuestionRepository;

    public MathQuestionServiceImpl(MathQuestionRepository mathQuestionRepository) {
        this.mathQuestionRepository = mathQuestionRepository;
    }
    @Override
    public Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException {
        return mathQuestionRepository.addQuestion(question, answer);
    }

    @Override
    public Question addQuestion(Question question) throws QuestionAlreadyExistsException {
        return mathQuestionRepository.addQuestion(question);
    }

    @Override
    public Question removeQuestion(Question questionText) throws QuestionNotExistsException {
        return mathQuestionRepository.removeQuestion(questionText);
    }

    @Override
    public Question findQuestion(String question) {
        Optional<Question> foundQuestion = getAllQuestions().stream()
                .filter(existingQuestion -> existingQuestion.getQuestion().equals(question))
                .findFirst();
        return foundQuestion.orElse(null);
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = getAllQuestions();
        return allQuestions.stream()
                .skip(new Random().nextInt(allQuestions.size()))
                .findFirst()
                .orElse(null);
    }

    public Collection<Question> getAmountOfQuestions(int amount) throws NotValidNumberException, NotEnoughQuestionException {
        if (getAllQuestions().isEmpty()|| amount == 0) {
            return new ArrayList<>();
        }
        if (!NumberValidator.isPositiveNumber(amount)) {
            throw new NotValidNumberException();
        }

        if (amount > getAllQuestions().size()) {
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
        return new ArrayList<>(mathQuestionRepository.getAllQuestions());
    }
}
