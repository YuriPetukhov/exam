package ru.skypro.exam.service;

import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionService {
    Question addJavaQuestion(String question, String answer) throws QuestionAlreadyExistsException;
    Question addJavaQuestion(Question question) throws QuestionAlreadyExistsException;
    Question removeJavaQuestion(Question question) throws QuestionNotExistsException;
    Question findJavaQuestion(String question) throws QuestionNotExistsException;
    Question getRandomJavaQuestion();
    Collection<Question> getAmountOfJavaQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException;
    Collection<Question> getAllJavaQuestions();
}
