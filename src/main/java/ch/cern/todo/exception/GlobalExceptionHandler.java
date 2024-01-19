package ch.cern.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest request) {
        return new ApiErrorResponse(ex.getMessage(), "Category Not Found");
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        return new ApiErrorResponse(ex.getMessage(), "Task Not Found");
    }

}
