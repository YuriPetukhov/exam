package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.Repository.JavaQuestionRepository;
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
        return javaQuestionRepository.getRandomQuestion();
    }

    @Override
    public Question findQuestion(String question) {
        return javaQuestionRepository.findQuestion(question);
    }
    @Override
    public Collection<Question> getAmountOfQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException {
        List<Question> questionList = new ArrayList<>(getAllQuestions());

        if (questionList.isEmpty()) {
            throw new QuestionNotExistsException();
        }
        if (!NumberValidator.isValidNumber(amount)) {
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
