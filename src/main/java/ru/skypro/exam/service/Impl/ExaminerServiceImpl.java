package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;
    private Random random;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) throws NotValidNumberException, NotEnoughQuestionException {
        if (!NumberValidator.isValidNumber(amount)) {
            throw new NotValidNumberException();
        }

        List<Question> availableQuestions = new ArrayList<>(questionService.getAllJavaQuestions());

        if (amount > availableQuestions.size()) {
            throw new NotEnoughQuestionException();
        }

        List<Question> randomQuestions = new ArrayList<>();
        while (randomQuestions.size() < amount){
            Question randomQuestion = questionService.getRandomJavaQuestion();
            if (!randomQuestions.contains(randomQuestion)) {
                randomQuestions.add(randomQuestion);
            }
        }

        return randomQuestions;
    }
}
