package uni.mainz.TrainingsTracker.model;

import java.sql.Date;

// Straight up from hell
public record Training(

        int workoutId,
        Date date,
        WorkoutType type,
        int workoutExerciseId,
        int workoutExerciseExerciseId,
        int exerciseId,
        String exerciseName,
        String exerciseDescription,
        boolean exercisePreSpecified,
        int setId,
        int setOrderNumber,
        int setWeight,
        int setRepetitions,
        int setWorkoutExerciseId

) { }
