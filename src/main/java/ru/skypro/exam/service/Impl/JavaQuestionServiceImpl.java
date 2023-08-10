package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
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

    public JavaQuestionServiceImpl(@Autowired JavaQuestionRepository javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }

    @Override
    public Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException {
        Question newQuestion = new Question(question, answer);
        return javaQuestionRepository.addQuestion(newQuestion);
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
    @Override
    public Collection<Question> getAmountOfQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException {
        List<Question> questionList = new ArrayList<>(getAllQuestions());

        if (questionList.isEmpty()) {
            throw new QuestionNotExistsException();
        }
        if (!NumberValidator.isPositiveNumber (amount)) {
            throw new NotValidNumberException();
        }
        if (amount > questionList.size()) {
            throw new NotEnoughQuestionException();
        }
        Collections.shuffle(questionList);
        return questionList.subList(0, Math.min(amount, questionList.size()));
    }
    @Override
    public Collection<Question> getAllQuestions() {
        return javaQuestionRepository.getAllQuestions();
    }

}
