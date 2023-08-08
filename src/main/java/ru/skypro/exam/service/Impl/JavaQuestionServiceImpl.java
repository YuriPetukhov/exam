package ru.skypro.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skypro.exam.Repository.JavaQuestionRepository;
import ru.skypro.exam.Repository.QuestionRepository;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service("javaQuestionService")
public class JavaQuestionServiceImpl implements QuestionService {
    private final JavaQuestionRepository javaQuestionRepository;

    public JavaQuestionServiceImpl(@Autowired JavaQuestionRepository javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }

    @Override
    public Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException, AnswerAlreadyExistsException {
        return javaQuestionRepository.addQuestion(question, answer);
    }
    @Override
    public Question removeQuestion(String questionText) throws QuestionNotExistsException {
        return javaQuestionRepository.removeQuestion(questionText);
    }

    @Override
    public Question findQuestion(String question) {
        Optional<Question> foundQuestion = getAllQuestions().stream()
                .filter(existingQuestion -> existingQuestion.getQuestion().equals(question))
                .findFirst();
        return foundQuestion.orElse(null);
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = getAllQuestions();
        return allQuestions.stream()
                .skip(new Random().nextInt(allQuestions.size()))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Collection<Question> getAmountOfQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException {
        List<Question> questionList = new ArrayList<>(getAllQuestions());

        if (questionList.isEmpty()) {
            throw new QuestionNotExistsException();
        }
        if (!NumberValidator.isValidNumber(amount)) {
            throw new NotValidNumberException();
        }
        if (amount > questionList.size()) {
            throw new NotEnoughQuestionException();
        }
        Collections.shuffle(questionList);
        return questionList.subList(0, Math.min(amount, questionList.size()));
    }
    @Override
    public Collection<Question> getAllQuestions() {
        return javaQuestionRepository.getAllQuestions();
    }
}
