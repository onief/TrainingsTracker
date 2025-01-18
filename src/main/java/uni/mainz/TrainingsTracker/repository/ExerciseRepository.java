package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import uni.mainz.TrainingsTracker.model.ExerciseRequest;
import uni.mainz.TrainingsTracker.model.ExerciseResponse;

import java.util.List;
import java.util.Optional;

@Repository
public class ExerciseRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExerciseRepository.class);
    private final JdbcClient jdbcClient;

    public ExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<ExerciseResponse> getAll() {
        return jdbcClient
                .sql("SELECT * FROM exercise")
                .query(ExerciseResponse.class)
                .list();
    }

    public Optional<ExerciseResponse> getById(int id) {
        return jdbcClient
                .sql("SELECT * FROM exercise WHERE id = :id")
                .param("id", id)
                .query(ExerciseResponse.class)
                .optional();
    }

    public Optional<ExerciseResponse> getByName(String name) {
        return jdbcClient
                .sql("SELECT * FROM exercise WHERE name = :name")
                .param("name", name)
                .query(ExerciseResponse.class)
                .optional();
    }

    public void create(ExerciseRequest exercise) {

    }

    public void update(ExerciseRequest exercise) {

    }

    public void delete(String identifier) {

    }
}
