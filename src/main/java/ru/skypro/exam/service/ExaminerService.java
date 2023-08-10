package ru.skypro.exam.service;

import ru.skypro.exam.exceptions.NotEnoughQuestionException;
import ru.skypro.exam.exceptions.NotValidNumberException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount) throws NotValidNumberException, NotEnoughQuestionException, QuestionNotExistsException;
}
