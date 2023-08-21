package ru.skypro.exam.formatter;

import ru.skypro.exam.model.Question;

public class QuestionFormatter {
        public static String formatQuestion(Question question, int questionNumber) {
            return "Question " + questionNumber + ": " + question.toString();
        }
}
