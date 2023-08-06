package ru.skypro.exam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AnswerAlreadyExistsException extends Exception{
    public AnswerAlreadyExistsException() {
        super("Ответ уже существует.");
    }
}
