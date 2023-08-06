package ru.skypro.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;

import java.util.Collection;
@RestController
@RequestMapping("/exam")
public class ExaminerServiceController {
    private final ExaminerService examinerService;

    public ExaminerServiceController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/questions")
    public Collection<Question> getQuestions(@RequestParam int amount) throws NotValidNumberException, NotEnoughQuestionException {
        return examinerService.getQuestions(amount);
    }
}
