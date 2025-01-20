package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PreSpecifiedException extends ResponseStatusException {
    public PreSpecifiedException() {
        super(HttpStatus.FORBIDDEN, "Cannot change pre-specified data.");
    }
}
