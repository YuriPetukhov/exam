package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.MethodNotAllowedException;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Collection<QuestionService> questionServices;

    @Autowired
    public ExaminerServiceImpl(Collection<QuestionService> questionServices) {
        this.questionServices = questionServices;
    }

    @Override
    public List<Question> getQuestions(int amount) throws NotEnoughQuestionException, NotValidNumberException, MethodNotAllowedException {
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

    private List<Question> getJavaQuestions(int amount) throws NotEnoughQuestionException, MethodNotAllowedException {
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

    private int getAllJavaQuestionsAmount() throws MethodNotAllowedException {
        int javaQuestionsAmount = 0;
        for (QuestionService questionService : questionServices) {
            if (questionService instanceof JavaQuestionServiceImpl) {
                Collection<Question> allJavaQuestions = questionService.getAllQuestions();
                javaQuestionsAmount += allJavaQuestions.size();
            }
        }
        return javaQuestionsAmount;
    }

    private Question getRandomJavaQuestion() throws MethodNotAllowedException {
        Question randomQuestion = null;
        for (QuestionService questionService : questionServices) {
            if (questionService instanceof JavaQuestionServiceImpl) {
                randomQuestion = questionService.getRandomQuestion();
                break;
            }
        }
        return randomQuestion;
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
        String question = random.nextInt(10) + " + " + random.nextInt(10);
        String answer = Integer.toString(Integer.parseInt(question.split(" ")[0]) + Integer.parseInt(question.split(" ")[2]));
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