package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpErrorResponse> handleRuntimeException(ResponseStatusException exception) {
        HttpErrorResponse error = new HttpErrorResponse(
                Instant.now(),
                exception.getStatusCode().value(),
                exception.getStatusCode().toString(),
                exception.getReason()
        );
        return new ResponseEntity<>(error, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleGeneralException(Exception ex) {
        HttpErrorResponse error = new HttpErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "An unexpected error occurred."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
