package uni.mainz.TrainingsTracker.dto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public record TrainingRequest(

        WorkoutRequest workout,
        // String is Name of Exercise
        Map<String, List<@Valid SetDTO>> exercises

) { }
