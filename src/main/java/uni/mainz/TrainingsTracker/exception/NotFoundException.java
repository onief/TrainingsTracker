package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String relation, String columnName) {
        super(HttpStatus.NOT_FOUND, String.format("%s '%s' not found.", relation, columnName));
    }
}
