package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@ControllerAdvice
public class HttpExceptionHandler {

    // There must be a better way: both Implement ErrorResponse, but it doesn't include a message
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpErrorResponse> handleStatusException(ResponseStatusException exception) {
        HttpErrorResponse error = new HttpErrorResponse(
                Instant.now(),
                exception.getStatusCode().value(),
                exception.getStatusCode().toString(),
                exception.getReason()
        );
        return new ResponseEntity<>(error, exception.getStatusCode());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleStatusException(NoResourceFoundException exception) {
        HttpErrorResponse error = new HttpErrorResponse(
                Instant.now(),
                exception.getStatusCode().value(),
                exception.getStatusCode().toString(),
                exception.getMessage()
        );
        return new ResponseEntity<>(error, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleGeneralException(Exception e) {
        HttpErrorResponse error = new HttpErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "An unexpected error occurred."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
