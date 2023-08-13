package ru.skypro.exam.repository;

import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException;
    Question addQuestion(Question question) throws QuestionAlreadyExistsException;
    Question removeQuestion(Question question) throws QuestionNotExistsException;
    Collection<Question> getAllQuestions();
}
