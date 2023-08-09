package ru.skypro.exam.service;

import ru.skypro.exam.exceptions.*;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionService {
    Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException, MethodNotAllowedException;
    Question addQuestion(Question question) throws QuestionAlreadyExistsException, MethodNotAllowedException;
    Question removeQuestion(Question question) throws QuestionNotExistsException, MethodNotAllowedException;
    Question findQuestion(String question) throws QuestionNotExistsException, MethodNotAllowedException;
    Question getRandomQuestion();
    Collection<Question> getAmountOfQuestions(int amount) throws QuestionNotExistsException, NotValidNumberException, NotEnoughQuestionException, MethodNotAllowedException;
    Collection<Question> getAllQuestions() throws MethodNotAllowedException;
}
