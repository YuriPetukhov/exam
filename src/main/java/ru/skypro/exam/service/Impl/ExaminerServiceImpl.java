package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.ExaminerService;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Map<Class<? extends QuestionService>, QuestionService> questionServicesMap;
    private final int maxMathUniqueQuestions;

    @Autowired
    public ExaminerServiceImpl(Collection<QuestionService> questionServices,
                               @Value("${mathQuestionService.maxUniqueQuestions}") int maxMathUniqueQuestions) {
        this.questionServicesMap = questionServices.stream()
                .collect(Collectors.toMap(QuestionService::getClass, Function.identity()));
        this.maxMathUniqueQuestions = maxMathUniqueQuestions;
    }

    private <T extends QuestionService> T getQuestionServiceByClass(Class<T> serviceClass) {
        return serviceClass.cast(Optional.ofNullable(questionServicesMap.get(serviceClass))
                .orElseThrow(() -> new IllegalStateException(serviceClass.getSimpleName() + " не найден")));
    }

    @Override
    public List<Question> getQuestions(int amount) throws NotValidNumberException, NotEnoughQuestionException {
        if (!NumberValidator.isPositiveNumber(amount)) {
            throw new NotValidNumberException();
        }

        JavaQuestionServiceImpl javaQuestionService = getQuestionServiceByClass(JavaQuestionServiceImpl.class);
        MathQuestionServiceImpl mathQuestionService = getQuestionServiceByClass(MathQuestionServiceImpl.class);

        int totalJavaQuestionsCount = javaQuestionService.getAllQuestions().size();
        int totalQuestionsCount = totalJavaQuestionsCount + maxMathUniqueQuestions;

        if (amount > totalQuestionsCount) {
            throw new NotEnoughQuestionException();
        }

        int maxJavaQuestionsCount = Math.min(amount, totalJavaQuestionsCount);
        Random random = new Random();
        int javaQuestionsCount = random.nextInt(maxJavaQuestionsCount + 1);
        int mathQuestionsCount = amount - javaQuestionsCount;

        return Stream.concat(javaQuestionService.getAmountOfQuestions(javaQuestionsCount).stream(), mathQuestionService.getAmountOfQuestions(mathQuestionsCount).stream())
                .collect(Collectors.toList());
    }
}

