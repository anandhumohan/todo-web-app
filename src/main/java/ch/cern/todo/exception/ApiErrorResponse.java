package ch.cern.todo.exception;

public class ApiErrorResponse {
    private final String message;
    private final String error;

    public ApiErrorResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
