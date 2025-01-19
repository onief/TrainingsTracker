package uni.mainz.TrainingsTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PreSpecifiedException extends RuntimeException {
    public PreSpecifiedException() {
        super("Cannot change pre-specified data.");
    }
}
