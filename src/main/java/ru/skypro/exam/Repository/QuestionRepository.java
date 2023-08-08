package ru.skypro.exam.Repository;

import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException, AnswerAlreadyExistsException;
    Question removeQuestion(String question) throws QuestionNotExistsException;
    Collection<Question> getAllQuestions();
}
