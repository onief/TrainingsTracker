package uni.mainz.TrainingsTracker.model;

import java.sql.Date;

public record WorkoutRequest(

        Date date,
        WorkoutType type

) { }
