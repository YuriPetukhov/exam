package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService javaQuestionService;
    private final QuestionService mathQuestionService;

    @Autowired
    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService javaQuestionService,
                               @Qualifier("mathQuestionService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public List<Question> getQuestions(int amount) throws NotEnoughQuestionException, NotValidNumberException {
        if (!NumberValidator.isValidNumber(amount)) {
            throw new NotValidNumberException();
        }
        Set<Question> questions = Stream.concat(
                        javaQuestionService.getAllQuestions().stream(),
                        mathQuestionService.getAllQuestions().stream())
                .collect(Collectors.toSet());
        if (amount > questions.size()) {
            throw new NotEnoughQuestionException();
        }
        List<Question> randomEmployees = new ArrayList<>(questions);

        Collections.shuffle(randomEmployees);

        return randomEmployees.stream()
                .limit(amount)
                .collect(Collectors.toList());
    }
}