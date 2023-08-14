package ru.skypro.exam.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
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

    @GetMapping("/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable int amount) throws NotEnoughQuestionException, NotValidNumberException, QuestionNotExistsException {
        return examinerService.getQuestions(amount);
    }
}
