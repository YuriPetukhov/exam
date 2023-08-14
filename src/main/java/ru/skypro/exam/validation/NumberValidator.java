package ru.skypro.exam.validation;

public class NumberValidator {
    public static boolean isPositiveNumber(int number) {
        return number >= 0;
    }
    public static boolean validateTotalQuestionsCount(int questionCount, int totalQuestionsCount){
        return questionCount <= totalQuestionsCount;
    }
}
