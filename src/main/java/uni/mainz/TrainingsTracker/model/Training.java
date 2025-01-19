package uni.mainz.TrainingsTracker.model;

import java.util.List;
import java.util.Map;

public record Training(

        Workout workout,
        Map<ExerciseResponse, List<Set>> exercises

) { }
