package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import uni.mainz.TrainingsTracker.controller.TrainingController;
import uni.mainz.TrainingsTracker.dto.TrainingResponse;
import uni.mainz.TrainingsTracker.model.Training;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingRepository {

    private static final Logger logger = LoggerFactory.getLogger(TrainingRepository.class);
    private final JdbcClient jdbcClient;

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    private final String basicSqlMapping = "workout.id as workoutId, " +
            "workout.date as date, " +
            "workout.type as type, " +
            "workout_exercise.id as workoutExerciseId, " +
            "workout_exercise.exercise as workoutExerciseExerciseId, " +
            "exercise.id as exerciseId, " +
            "exercise.name as exerciseName, " +
            "exercise.description as exerciseDescription, " +
            "exercise.pre_specified as exercisePreSpecified, " +
            "set.id as setId, " +
            "set.order_number as setOrderNumber, " +
            "set.weight as setWeight, " +
            "set.repetitions as setRepetitions, " +
            "set.workout_exercise as setWorkoutExerciseId ";

    private final String basicSqlJoin = "join workout_exercise on workout.id = workout_exercise.workout " +
            "join exercise on workout_exercise.exercise = exercise.id " +
            "join set on workout_exercise.id = set.workout_exercise ";

    public TrainingRepository(JdbcClient jdbcClient, WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.jdbcClient = jdbcClient;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<Training> getAll() {
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin)
                .query(Training.class)
                .list();
    }

    public List<Training> getById(int id) {
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin + "where workout.id = ?")
                .params(List.of(id))
                .query(Training.class)
                .list();
    }
}
