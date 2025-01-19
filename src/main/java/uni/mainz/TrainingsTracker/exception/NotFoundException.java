package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String relation, String columnName) {
        super(String.format("%s '%s' not found.", relation, columnName));
    }
}
