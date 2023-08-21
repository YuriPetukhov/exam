package ru.skypro.exam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotEnoughQuestionException extends Exception{
    public NotEnoughQuestionException() {
        super ("Недостаточно вопросов.");
    }
}
