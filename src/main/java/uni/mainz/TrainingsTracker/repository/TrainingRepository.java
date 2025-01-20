package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import uni.mainz.TrainingsTracker.controller.TrainingController;
import uni.mainz.TrainingsTracker.dto.SetDTO;
import uni.mainz.TrainingsTracker.dto.TrainingRequest;
import uni.mainz.TrainingsTracker.dto.TrainingResponse;
import uni.mainz.TrainingsTracker.dto.WorkoutRequest;
import uni.mainz.TrainingsTracker.exception.ConflictException;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.model.Training;
import uni.mainz.TrainingsTracker.model.WorkoutType;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingRepository {

    private static final Logger logger = LoggerFactory.getLogger(TrainingRepository.class);
    private final JdbcClient jdbcClient;

    public final WorkoutRepository workoutRepository;
    public final ExerciseRepository exerciseRepository;

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
        if (workoutRepository.getAll().isEmpty()) {
            return List.of();
        }
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin)
                .query(Training.class)
                .list();
    }

    public List<Training> getById(int id) {
        if (workoutRepository.getById(id).isEmpty()) {
            return List.of();
        }
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin + "where workout.id = ?")
                .params(List.of(id))
                .query(Training.class)
                .list();
    }

    public List<Training> getByParams(WorkoutType type) {
        if (workoutRepository.getByParams(type).isEmpty()) {
            return List.of();
        }
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin + "where workout.type = :type")
                .param("type", type.toString())
                .query(Training.class)
                .list();
    }

    public List<Training> getByParams(Date date) {
        if (workoutRepository.getByParams(date).isEmpty()) {
            return List.of();
        }
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin + "where workout.date = :date")
                .param("date", date)
                .query(Training.class)
                .list();
    }

    public List<Training> getByParams(Date date, WorkoutType type) {
        if (workoutRepository.getByParams(date).isEmpty()) {
            return List.of();
        }
        return jdbcClient
                .sql("select " + basicSqlMapping + "from workout " + basicSqlJoin + "where workout.type = ? and workout.date = ?")
                .params(List.of(type.toString(), date))
                .query(Training.class)
                .list();
    }

    public void create(TrainingRequest trainingRequest) {
        WorkoutRequest workout = trainingRequest.workout();
        Map<String, List<SetDTO>> exercises = trainingRequest.exercises();

        workoutRepository.create(workout);
        int workoutId = jdbcClient.sql("select max(id) from workout;").query(Integer.class).single();

        for (var entry : exercises.entrySet()) {
            String exercise = entry.getKey();
            List<SetDTO> sets = entry.getValue();

            Integer exerciseId = jdbcClient
                    .sql("select id from exercise where name = :name")
                    .param("name", exercise)
                    .query(Integer.class)
                    .single();

            Integer workoutExerciseId = jdbcClient
                    .sql("insert into workout_exercise (workout, exercise) values (?, ?) returning id")
                    .params(List.of(workoutId, exerciseId))
                    .query(Integer.class)
                    .single();

            for (SetDTO set : sets) {
                jdbcClient
                        .sql("insert into set (order_number, weight, repetitions, workout_exercise) values (?, ?, ?, ?)")
                        .params(List.of(set.order_number(), set.weight(), set.repetitions(), workoutExerciseId))
                        .update();
            }
        }


    }

    public void delete(int id) {

        int workoutCode = workoutRepository.delete(id);
        logger.info(String.valueOf(workoutCode));
    }
}
