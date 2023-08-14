package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService javaQuestionService;
    private final QuestionService mathQuestionService;
    private final Random random = new Random();

    @Autowired
    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService javaQuestionService,
                               @Qualifier("mathQuestionService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    public List<Question> getQuestions(int amount) throws NotEnoughQuestionException, NotValidNumberException, QuestionNotExistsException {
        if (!NumberValidator.isPositiveNumber(amount)) {
            throw new NotValidNumberException();
        }
        int totalJavaQuestionsCount = javaQuestionService.getAllQuestions().size();
        int totalMathQuestionsCount = mathQuestionService.getAllQuestions().size();
        int totalQuestionsCount = totalJavaQuestionsCount + totalMathQuestionsCount;

        if(!NumberValidator.validateTotalQuestionsCount(amount, totalQuestionsCount)){
            throw new NotEnoughQuestionException();
        }

        int maxJavaQuestionsCount = Math.min(amount, totalJavaQuestionsCount);
        int maxMathQuestionsCount = Math.min(amount, totalMathQuestionsCount);
        int minJavaQuestionsCount = amount - maxMathQuestionsCount;

        int javaQuestionsCount = getRandomNumberInRange(minJavaQuestionsCount, maxJavaQuestionsCount);
        int mathQuestionsCount = amount - javaQuestionsCount;

        List<Question> questions = new ArrayList<>(amount);
        questions.addAll(javaQuestionService.getAmountOfQuestions(javaQuestionsCount));
        questions.addAll(mathQuestionService.getAmountOfQuestions(mathQuestionsCount));

        Collections.shuffle(questions);

        return questions;
    }
    protected int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return random.nextInt((max - min) + 1) + min;
    }
}