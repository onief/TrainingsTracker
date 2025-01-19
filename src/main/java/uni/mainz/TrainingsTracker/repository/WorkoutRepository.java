package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import uni.mainz.TrainingsTracker.model.WorkoutRequest;
import uni.mainz.TrainingsTracker.model.WorkoutResponse;
import uni.mainz.TrainingsTracker.model.WorkoutType;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkoutRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(WorkoutRepository.class);
    private final JdbcClient jdbcClient;

    public WorkoutRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<WorkoutResponse> getAll() {
        return null;
    }

    public Optional<WorkoutResponse> getById(int id) {
        return null;
    }

    public List<WorkoutResponse> getByParams(Date date) {
        return null;
    }

    public List<WorkoutResponse> getByParams(WorkoutType workoutType) {
        return null;
    }

    public List<WorkoutResponse> getByParams(Date date, WorkoutType workoutType) {
        return null;
    }

    public boolean create(WorkoutRequest workoutRequest) {
        return false;
    }

    public boolean update(WorkoutRequest workoutRequest, int id) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

}
