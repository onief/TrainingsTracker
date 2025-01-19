package uni.mainz.TrainingsTracker.dto;

import uni.mainz.TrainingsTracker.model.WorkoutType;

import java.sql.Date;

public record WorkoutRequest(

        Date date,
        WorkoutType type

) { }
