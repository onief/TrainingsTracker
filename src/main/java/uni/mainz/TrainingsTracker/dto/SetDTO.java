package uni.mainz.TrainingsTracker.dto;

import jakarta.validation.constraints.Positive;

public record SetDTO(

        @Positive
        int order_number,
        @Positive
        int weight,
        @Positive
        int repetitions
) { }
