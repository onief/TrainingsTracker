package uni.mainz.TrainingsTracker.dto;

import uni.mainz.TrainingsTracker.model.WorkoutType;

import java.sql.Date;

public record WorkoutResponse(

        int id,
        Date date,
        WorkoutType type

) { }
