package ru.skypro.exam.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {
    private final QuestionService questionService;

    public MathQuestionController(@Qualifier("mathQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/add")
    public String addMathQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionAlreadyExistsException {
        return "Вопрос успешно добавлен: " + questionService.addQuestion(question, answer);
    }
    @GetMapping("/remove")
    public String removeMathQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionNotExistsException {
        Question questionToRemove = new Question(question, answer);
        Question removedQuestion = questionService.removeQuestion(questionToRemove);
        return "Вопрос успешно удален: " + removedQuestion.getQuestion();
    }
    @GetMapping("/find")
    public ResponseEntity<?> findMathQuestion(@RequestParam String question) throws QuestionNotExistsException {
        Question foundQuestion = questionService.findQuestion(question);
        return ResponseEntity.ok(Objects.requireNonNullElse(foundQuestion, "Вопрос не найден"));
    }
    @GetMapping("/random")
    public Question getRandomMathQuestion(){
        return questionService.getRandomQuestion();
    }
    @GetMapping("/get/{amount}")
    public Collection<Question> getAmountOfMathQuestions(@PathVariable int amount) throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        return questionService.getAmountOfQuestions(amount);
    }
    @GetMapping("/getAll")
    public Collection<Question> getAllMathQuestions() {
        return questionService.getAllQuestions();
    }
}