package ru.skypro.exam.service;

import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionService {
    Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException;

    Question addQuestion(Question question) throws QuestionAlreadyExistsException;

    Question removeQuestion(Question question) throws QuestionNotExistsException;
    Question findQuestion(String question) throws QuestionNotExistsException;
    Question getRandomQuestion();
    Collection<Question> getAmountOfQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException;
    Collection<Question> getAllQuestions();
}
