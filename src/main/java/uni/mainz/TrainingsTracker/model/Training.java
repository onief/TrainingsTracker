package uni.mainz.TrainingsTracker.model;

import java.util.List;
import java.util.Map;

public record Training(

        WorkoutResponse workout,
        Map<ExerciseResponse, List<Set>> exercises

) { }
