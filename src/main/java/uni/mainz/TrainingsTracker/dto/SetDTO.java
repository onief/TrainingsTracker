package uni.mainz.TrainingsTracker.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record SetDTO(

        @Positive
        int order_number,
        @PositiveOrZero
        int weight,
        @Positive
        int repetitions
) { }
