package ru.skypro.exam.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.formatter.QuestionFormatter;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {
    private final QuestionService questionService;

    public MathQuestionController(@Qualifier("mathQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/add")
    public String addMathQuestion(@RequestParam String question, @RequestParam String answer) throws QuestionAlreadyExistsException, MethodNotAllowedException {
        return "Вопрос успешно добавлен: " + questionService.addQuestion(question, answer);
    }
    @GetMapping("/remove")
    public String removeJavaQuestion(@RequestParam String question) throws QuestionNotExistsException, MethodNotAllowedException {
        Question foundQuestion = questionService.findQuestion(question);
        if (foundQuestion == null) {
            throw new QuestionNotExistsException();
        }
        Question removedQuestion = questionService.removeQuestion(foundQuestion);
        return "Вопрос успешно удален: " + removedQuestion.getQuestion();
    }
    @GetMapping("/find")
    public String findJavaQuestion(@RequestParam String question) throws QuestionNotExistsException, MethodNotAllowedException {
        Question foundQuestion = questionService.findQuestion(question);
        if (foundQuestion == null) {
            throw new QuestionNotExistsException();
        }
        return foundQuestion.getQuestion() + ", " + foundQuestion.getAnswer();
    }
    @GetMapping("/random")
    public String getRandomMathQuestion(){
        Question randomQuestion = questionService.getRandomQuestion();
        return randomQuestion.getQuestion() + " = " + randomQuestion.getAnswer();
    }
    @GetMapping("/get/{amount}")
    public List<String> getAmountOfMathQuestions(@PathVariable int amount) throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException, MethodNotAllowedException {
        Collection<Question> questions = questionService.getAmountOfQuestions(amount);
        List<String> formattedQuestions = new ArrayList<>();
        int questionNumber = 1;
        for (Question question : questions) {
            formattedQuestions.add(QuestionFormatter.formatQuestion(question, questionNumber));
            questionNumber++;
        }
        return formattedQuestions;
    }
    @GetMapping("/getAll")
    public List<String> getAllJavaQuestions() throws MethodNotAllowedException {
        List<Question> allQuestions = new ArrayList<>(questionService.getAllQuestions());
        List<String> formattedQuestions = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            formattedQuestions.add(QuestionFormatter.formatQuestion(allQuestions.get(i), i + 1));
        }
        return formattedQuestions;
    }
}