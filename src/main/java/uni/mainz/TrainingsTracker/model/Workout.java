package uni.mainz.TrainingsTracker.model;

import java.sql.Date;

public record Workout(

        int id,         // unnecessary?
        Date date,
        String type

) { }
