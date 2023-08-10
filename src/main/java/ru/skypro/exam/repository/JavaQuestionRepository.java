package ru.skypro.exam.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.util.*;

@Repository("javaQuestionRepository")
public class JavaQuestionRepository implements QuestionRepository{

    private final Map<String, Question> questions;

    public JavaQuestionRepository() {
        this.questions = new HashMap<>();
    }

    public JavaQuestionRepository(Map<String, Question> questions) {
        this.questions = questions;
    }
    public Map<String, Question> getQuestions() {
        return questions;
    }

    @Override
    public Question addQuestion(Question question) throws QuestionAlreadyExistsException {
        String questionText = question.getQuestion();

        boolean isDuplicateQuestion = questions.values().stream().anyMatch(q -> q.getQuestion().equals(questionText));

        if (isDuplicateQuestion) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(question.getId(), question);
        return question;
    }

    @Override
    public Question removeQuestion(Question question) throws QuestionNotExistsException {
        String questionId = question.getId();

        if (questions.containsKey(questionId)) {
            Question removedQuestion = questions.remove(questionId);
            return removedQuestion;
        } else {
            throw new QuestionNotExistsException();
        }
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return questions.values();
    }
}
