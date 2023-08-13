package ru.skypro.exam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionsController {
    private final QuestionService questionService;

    public JavaQuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/add")
    public String addJavaQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionAlreadyExistsException {
        return "Вопрос успешно добавлен: " + questionService.addJavaQuestion(question, answer);
    }
    @GetMapping("/remove")
    public String removeJavaQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionNotExistsException {
        Question questionToRemove = new Question(question, answer);
        Question removedQuestion = questionService.removeJavaQuestion(questionToRemove);
        return "Вопрос успешно удален: " + removedQuestion.getQuestion();
    }
    @GetMapping("/find")
    public ResponseEntity<?> findJavaQuestion(@RequestParam String question) throws QuestionNotExistsException {
        Question foundQuestion = questionService.findJavaQuestion(question);
        return ResponseEntity.ok(Objects.requireNonNullElse(foundQuestion, "Вопрос не найден"));
    }
    @GetMapping("/random")
    public Question getRandomJavaQuestion(){
        return questionService.getRandomJavaQuestion();
    }
    @GetMapping("/get/{amount}")
    public Collection<Question> getAmountOfJavaQuestions(@PathVariable int amount) throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        return questionService.getAmountOfJavaQuestions(amount);
    }
    @GetMapping("/getAll")
    public Collection<Question> getAllJavaQuestions() {
        return questionService.getAllJavaQuestions();
    }
}
