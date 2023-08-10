package ru.skypro.exam.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionsController {
    private final QuestionService questionService;

    public JavaQuestionsController(@Qualifier("javaQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/add")
    public String addJavaQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionAlreadyExistsException {
        return "Вопрос успешно добавлен: " + questionService.addQuestion(question, answer);
    }
    @GetMapping("/remove")
    public String removeJavaQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionNotExistsException {
        Question questionToRemove = new Question(question, answer);
        Question removedQuestion = questionService.removeQuestion(questionToRemove);
        return "Вопрос успешно удален: " + removedQuestion.getQuestion();
    }
    @GetMapping("/find")
    public ResponseEntity<?> findJavaQuestion(@RequestParam String question) throws QuestionNotExistsException {
        Question foundQuestion = questionService.findQuestion(question);
        return ResponseEntity.ok(Objects.requireNonNullElse(foundQuestion, "Вопрос не найден"));
    }
    @GetMapping("/random")
    public Question getRandomJavaQuestion(){
        return questionService.getRandomQuestion();
    }
    @GetMapping("/get/amount")
    public Collection<Question> getAmountOfJavaQuestions(@RequestParam int amount) throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException {
        return questionService.getAmountOfQuestions(amount);
    }
    @GetMapping("/getAll")
    public Collection<Question> getAllJavaQuestions() {
        return questionService.getAllQuestions();
    }
}
