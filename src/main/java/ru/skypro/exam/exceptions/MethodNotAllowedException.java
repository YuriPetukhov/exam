package ru.skypro.exam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends Exception{
    public MethodNotAllowedException()  {
        super("Не допустимое действие.");
    }
}
