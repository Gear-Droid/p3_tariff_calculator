package ru.fastdelivery.presentation.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {
        ApiError apiError = ApiError.badRequest("Не удалось распознать входящий запрос!"
                + " Проверьте правильность вашего JSON объекта!");

        return new ResponseEntity<>(apiError, apiError.httpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        String messageFormat = "Не удалось выполнить расчет! Причина ошибки: [%s]";
        ApiError apiError = ApiError.badRequest(messageFormat.formatted(ex.getMessage()));

        return new ResponseEntity<>(apiError, apiError.httpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errorMessage = String.join("; ", errorMessages);
        ApiError apiError = ApiError.badRequest(errorMessage);

        return new ResponseEntity<>(apiError, apiError.httpStatus());
    }
}

