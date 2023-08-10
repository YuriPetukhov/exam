package ru.skypro.exam.Repository;

import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException;
    Question addQuestion(Question question) throws QuestionAlreadyExistsException;
    Question removeQuestion(Question question) throws QuestionNotExistsException;
    Question findQuestion(String question);
    Collection<Question> getAllQuestions();
    Question getRandomQuestion();
}
