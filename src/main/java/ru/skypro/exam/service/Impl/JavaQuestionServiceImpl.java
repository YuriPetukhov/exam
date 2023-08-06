package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;

@Service
public class JavaQuestionServiceImpl implements QuestionService {
    private final Collection<Question> questions = new HashSet<>();

    @Override
    public Question addJavaQuestion(String question, String answer) throws QuestionAlreadyExistsException, AnswerAlreadyExistsException {
        boolean questionExists = questions.stream().anyMatch(existingQuestion -> existingQuestion.getQuestion().equals(question));
        boolean answerExists = questions.stream().anyMatch(existingQuestion -> existingQuestion.getAnswer().equals(answer));

        if (questionExists || answerExists) {
            if (questionExists) {
                throw new QuestionAlreadyExistsException();
            } else {
                throw new AnswerAlreadyExistsException();
            }
        }

        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question removeJavaQuestion(String questionText) throws QuestionNotExistsException {
        Optional<Question> removedQuestion = questions.stream()
                .filter(existingQuestion -> existingQuestion.getQuestion().equals(questionText))
                .findFirst();
        if (removedQuestion.isPresent()) {
            questions.remove(removedQuestion.get());
            return removedQuestion.get();
        } else {
            throw new QuestionNotExistsException();
        }
    }

    @Override
    public Question findJavaQuestion(String question) {
        Optional<Question> foundQuestion = questions.stream()
                .filter(existingQuestion -> existingQuestion.getQuestion().equals(question))
                .findFirst();
        return foundQuestion.orElse(null);
    }

    @Override
    public Question getRandomJavaQuestion() {
        return questions.stream()
                .skip(new Random().nextInt(questions.size()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Question> getAmountOfJavaQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException {
        List<Question> questionList = new ArrayList<>(questions);

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
    public Collection<Question> getAllJavaQuestions() {
        return questions;
    }
}
