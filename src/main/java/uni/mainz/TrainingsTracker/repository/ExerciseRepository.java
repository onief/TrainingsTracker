package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import uni.mainz.TrainingsTracker.dto.ExerciseRequest;
import uni.mainz.TrainingsTracker.dto.ExerciseResponse;

import java.util.List;
import java.util.Optional;

@Repository
public class ExerciseRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExerciseRepository.class);
    private final JdbcClient jdbcClient;

    public ExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private int checkData(String name) {
        Optional<ExerciseResponse> current = getByName(name);
        if (current.isEmpty()) {
            return 1;
        }
        if (current.get().pre_specified()) {
            return 2;
        }
        return 0;
    }

    public List<ExerciseResponse> getAll() {
        return jdbcClient
                .sql("SELECT * FROM exercise")
                .query(ExerciseResponse.class)
                .list();
    }

    public Optional<ExerciseResponse> getByName(String name) {
        return jdbcClient
                .sql("SELECT * FROM exercise WHERE name = :name")
                .param("name", name)
                .query(ExerciseResponse.class)
                .optional();
    }

    public int create(ExerciseRequest exercise) {
        if (getByName(exercise.name()).isPresent()) {
            return 1;
        }

        jdbcClient
                .sql("INSERT INTO exercise (name, description, pre_specified) VALUES (?, ?, ?)")
                .params(List.of(exercise.name(), exercise.description(), false))
                .update();

        return 0;

    }

    public int update(ExerciseRequest exercise, String name) {
        int dataCheck = checkData(name);
        if (dataCheck != 0) {
            return dataCheck;
        }

        jdbcClient
                .sql("UPDATE exercise SET name = ? , description = ? WHERE name = ?")
                .params(List.of(exercise.name(), exercise.description(), name))
                .update();

        return 0;
    }

    public int delete(String name) {
        int dataCheck = checkData(name);
        if (dataCheck != 0) {
            return dataCheck;
        }

        Integer id = jdbcClient
                .sql("SELECT id FROM exercise WHERE name = :name")
                .param("name", name)
                .query(Integer.class)
                .single();

        int hasDependentData = jdbcClient
                .sql("SELECT id FROM workout_exercise where workout_exercise.exercise = :id")
                .param("id", id)
                .query(Integer.class)
                .list()
                .size();

        if (hasDependentData != 0) {
            return 3;
        }

        jdbcClient
                .sql("DELETE FROM exercise WHERE name = :name")
                .param("name", name)
                .update();

        return 0;
    }
}
