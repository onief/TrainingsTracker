package uni.mainz.TrainingsTracker.dto;

import uni.mainz.TrainingsTracker.model.Set;

import java.util.List;
import java.util.Map;

public record TrainingResponse(

        WorkoutResponse workout,
        // String is Name of Exercise
        Map<String, List<SetDTO>> exercises

) { }
