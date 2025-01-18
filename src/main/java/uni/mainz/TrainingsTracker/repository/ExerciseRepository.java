package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import uni.mainz.TrainingsTracker.model.Exercise;

import java.util.List;

@Repository
public class ExerciseRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExerciseRepository.class);
    private final JdbcClient jdbcClient;

    public ExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Exercise> getAllExercises() {
        return jdbcClient
                .sql("select * from exercise")
                .query(Exercise.class)
                .list();
    }
}
