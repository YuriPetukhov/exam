package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Collection<QuestionService> questionServices;

    @Autowired
    public ExaminerServiceImpl(Collection<QuestionService> questionServices) {
        this.questionServices = questionServices;
    }
    private static final Class<?> JAVA_QUESTION_SERVICE_CLASS = JavaQuestionServiceImpl.class;
    @Override
    public List<Question> getQuestions(int amount) throws NotValidNumberException, MethodNotAllowedException {
        if (!NumberValidator.isPositiveNumber(amount)) {
            throw new NotValidNumberException();
        }
        List<Question> javaQuestions = getJavaQuestions(amount);
        List<Question> mathQuestions = getMathQuestions(amount - javaQuestions.size());
        List<Question> questions = new ArrayList<>(javaQuestions);
        questions.addAll(mathQuestions);
        Collections.shuffle(questions);
        return questions;
    }
    private List<Question> getJavaQuestions(int amount) throws MethodNotAllowedException {
        List<Question> javaQuestions = new ArrayList<>();

        int javaQuestionsAmount = getAllJavaQuestionsAmount();
        int limit = Math.min(amount, javaQuestionsAmount);

        while (javaQuestions.size() < limit) {
            Question randomQuestion = getRandomJavaQuestion();
            if (javaQuestions.contains(randomQuestion)) {
                continue;
            }
            javaQuestions.add(randomQuestion);
        }
        return javaQuestions;
    }
    private int getAllJavaQuestionsAmount() {
        return questionServices.stream()
                .filter(service -> service.getClass().equals(JAVA_QUESTION_SERVICE_CLASS))
                .mapToInt(service -> {
                    try {
                        return service.getAllQuestions().size();
                    } catch (MethodNotAllowedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }
    private Question getRandomJavaQuestion() {
        return questionServices.stream()
                .filter(service -> service.getClass().equals(JAVA_QUESTION_SERVICE_CLASS))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("JavaQuestionServiceImpl не найден"))
                .getRandomQuestion();
    }
    private List<Question> getMathQuestions(int amount) {
        List<Question> mathQuestions = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            mathQuestions.add(getRandomMathQuestion());
        }
        return mathQuestions;
    }
    private Question getRandomMathQuestion() {
        Random random = new Random();
        int firstNumber = random.nextInt(10);
        int secondNumber = random.nextInt(10);
        String question = firstNumber + " + " + secondNumber;
        String answer = Integer.toString(firstNumber + secondNumber);
        return new Question(question, answer);
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