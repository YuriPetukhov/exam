package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service("mathQuestionService")
public class MathQuestionServiceImpl implements QuestionService {
    private final Random random = new Random();

    public MathQuestionServiceImpl() {
    }

    @Override
    public Question addQuestion(String question, String answer) throws MethodNotAllowedException {
        throw new MethodNotAllowedException();
    }
    @Override
    public Question addQuestion(Question question) throws MethodNotAllowedException {
        throw new MethodNotAllowedException();
    }

    @Override
    public Question removeQuestion(Question questionText) throws MethodNotAllowedException {
        throw new MethodNotAllowedException();
    }

    @Override
    public Question findQuestion(String question) throws MethodNotAllowedException {
        throw new MethodNotAllowedException();
    }

    @Override
    public Question getRandomQuestion() {
        String question = random.nextInt(10) + " + " + random.nextInt(10);
        String answer = Integer.toString(Integer.parseInt(question.split(" ")[0]) + Integer.parseInt(question.split(" ")[2]));
        return new Question(question, answer);
    }

    @Override
    public List<Question> getAmountOfQuestions(int amount) throws NotValidNumberException {
        if (amount == 0) {
            return new ArrayList<>();
        }
        if (!NumberValidator.isPositiveNumber(amount)) {
            throw new NotValidNumberException();
        }

        Set<Question> resultSet = new HashSet<>();
        while (resultSet.size() < amount) {
            Question randomQuestion = getRandomQuestion();
            resultSet.add(randomQuestion);
        }

        return new ArrayList<>(resultSet);
    }

    @Override
    public List<Question> getAllQuestions() throws MethodNotAllowedException {
        throw new MethodNotAllowedException();
    }
}
