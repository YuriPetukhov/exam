package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Collection<QuestionService> questionServices;

    @Autowired
    public ExaminerServiceImpl(Collection<QuestionService> questionServices) {
        this.questionServices = questionServices;
    }

    @Override
    public List<Question> getQuestions(int amount) throws NotEnoughQuestionException, NotValidNumberException {
        if (!NumberValidator.isValidNumber(amount)) {
            throw new NotValidNumberException();
        }

        List<Question> allQuestions = new ArrayList<>();

        questionServices.forEach(questionService -> {
            try {
                allQuestions.addAll(questionService.getAllQuestions());
            } catch (MethodNotAllowedException e) {
                throw new RuntimeException(e);
            }
        });

        if (amount > allQuestions.size()) {
            throw new NotEnoughQuestionException();
        }

        Collections.shuffle(allQuestions);

        return allQuestions.stream()
                .limit(amount)
                .collect(Collectors.toList());
    }
    @Override
    public Collection<Question> getAllQuestions() throws MethodNotAllowedException {
        List<Question> allQuestions = new ArrayList<>();
        for (QuestionService questionService : questionServices) {
            allQuestions.addAll(questionService.getAllQuestions());
        }
        return allQuestions;
    }
}