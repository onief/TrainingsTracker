package uni.mainz.TrainingsTracker.exception;

import java.time.Instant;

public record HttpErrorResponse(

        Instant timestamp,
        int status,
        String error,
        String message

) { }
