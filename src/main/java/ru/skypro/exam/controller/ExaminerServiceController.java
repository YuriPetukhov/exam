package ru.skypro.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.formatter.QuestionFormatter;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExaminerServiceController {
    private final ExaminerService examinerService;

    public ExaminerServiceController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/questions")
    public Collection<String> getQuestions(@RequestParam int amount) throws NotEnoughQuestionException, NotValidNumberException, MethodNotAllowedException, QuestionNotExistsException {
        Collection<Question> questions = examinerService.getQuestions(amount);

        List<String> formattedQuestions = new ArrayList<>();

        int questionNumber = 1;
        for (Question question : questions) {
            formattedQuestions.add(QuestionFormatter.formatQuestion(question, questionNumber));
            questionNumber++;
        }
        return formattedQuestions;
    }
}