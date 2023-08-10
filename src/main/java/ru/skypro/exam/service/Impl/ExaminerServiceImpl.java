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
        List<Question> javaQuestions = new ArrayList<>(questionService.getAllJavaQuestions());

        if (amount > javaQuestions.size()) {
            throw new NotEnoughQuestionException();
        }
        List<Question> selectionPool = new ArrayList<>(javaQuestions);
        Set<Question> selectedQuestions = new HashSet<>();

        while (selectedQuestions.size() < amount && !selectionPool.isEmpty()) {
            Question randomQuestion = questionService.getRandomJavaQuestion();
            if (selectionPool.contains(randomQuestion)) {
                selectedQuestions.add(randomQuestion);
                selectionPool.remove(randomQuestion);
            }
        }
        return selectedQuestions;
    }
}
