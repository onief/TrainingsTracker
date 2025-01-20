package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictException extends ResponseStatusException {
    public ConflictException(String relation, String columnName) {
        super(HttpStatus.CONFLICT, String.format("%s with identifier '%s' already exists.", relation, columnName));
    }
}
