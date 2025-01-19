package uni.mainz.TrainingsTracker.model;

import java.sql.Date;

public record Workout(

        int id,
        Date date,
        WorkoutType type

) { }
