package uni.mainz.TrainingsTracker.model;

import jakarta.validation.constraints.Positive;

public record Set(

        int id,                     // unnecessary?
        @Positive
        int order_number,
        @Positive
        int weight,
        @Positive
        int repetitions,
        int workout_exercise        // unnecessary?

) { }
