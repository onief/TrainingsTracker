package uni.mainz.TrainingsTracker.model;

import java.sql.Date;

public record WorkoutResponse(

        int id,
        Date date,
        WorkoutType type

) { }
