package ru.skypro.exam.service.Impl;

import org.springframework.stereotype.Service;
import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;
import ru.skypro.exam.service.QuestionService;
import ru.skypro.exam.validation.NumberValidator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JavaQuestionServiceImpl implements QuestionService {
    private final Map<String, String> questions;
    public JavaQuestionServiceImpl() {
        this.questions = new HashMap<>();
    }
    @Override
    public Question addJavaQuestion(String question, String answer) throws QuestionAlreadyExistsException {
        if (questions.containsKey(question)) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(question, answer);
        return new Question(question, answer);
    }
    @Override
    public Question addJavaQuestion(Question question) throws QuestionAlreadyExistsException {
        if (questions.containsKey(question.getQuestion())) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(question.getQuestion(), question.getAnswer());
        return new Question(question.getQuestion(), question.getAnswer());
    }
    @Override
    public Question removeJavaQuestion(Question question) throws QuestionNotExistsException {
        Question foundQuestion = findJavaQuestion(question.getQuestion());
        if (foundQuestion == null) {
            throw new QuestionNotExistsException();
        }
        questions.remove(question.getQuestion());
        return foundQuestion;
    }
    @Override
    public Question findJavaQuestion(String question) {
        if (questions.containsKey(question)) {
            String answer = questions.get(question);
            return new Question(question, answer);
        } else {
            return null;
        }
    }
    @Override
    public Question getRandomJavaQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(questions.size());
        Map.Entry<String, String> randomEntry =
                new ArrayList<>(questions.entrySet()).get(randomIndex);
        return new Question(randomEntry.getKey(), randomEntry.getValue());
    }
    @Override
    public Collection<Question> getAmountOfJavaQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException {
        if (questions.isEmpty()) {
            throw new QuestionNotExistsException();
        }
        if (!NumberValidator.isValidNumber(amount)) {
            throw new NotValidNumberException();
        }
        if (amount > questions.size()) {
            throw new NotEnoughQuestionException();
        }
        List<Question> questionList = questions.entrySet().stream()
                .map(entry -> new Question(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        Collections.shuffle(questionList);
        return questionList.subList(0, Math.min(amount, questionList.size()));
    }

    @Override
    public Collection<Question> getAllJavaQuestions() {
        return questions.entrySet().stream()
                .map(entry -> new Question(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
