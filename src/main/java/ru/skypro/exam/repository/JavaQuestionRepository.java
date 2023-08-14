package ru.skypro.exam.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.util.*;
import java.util.stream.Collectors;

@Repository("javaQuestionRepository")
public class JavaQuestionRepository implements QuestionRepository{
    private final Map<String, String> questions;
    public JavaQuestionRepository() {
        this.questions = new HashMap<>();
    }
    @Override
    public Question addQuestion(String questionText, String answer) throws QuestionAlreadyExistsException {
        if (questions.containsKey(questionText)) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(questionText, answer);
        return new Question(questionText, answer);
    }
    @Override
    public Question addQuestion(Question question) throws QuestionAlreadyExistsException {
        if (questions.containsKey(question.getQuestion())) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(question.getQuestion(), question.getAnswer());
        return new Question(question.getQuestion(), question.getAnswer());
    }

    @Override
    public Question removeQuestion(Question question) throws QuestionNotExistsException {
        Optional<Question> foundQuestion = getAllQuestions().stream()
                .filter(q -> q.getQuestion().equals(question.getQuestion()))
                .findFirst();
        if (foundQuestion.isEmpty()) {
            throw new QuestionNotExistsException();
        }
        questions.remove(question.getQuestion());
        return foundQuestion.get();
    }
    @Override
    public List<Question> getAllQuestions() {
        return questions.isEmpty() ?
                Collections.emptyList() :
                questions.entrySet().stream()
                        .map(entry -> new Question(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
    }
}
