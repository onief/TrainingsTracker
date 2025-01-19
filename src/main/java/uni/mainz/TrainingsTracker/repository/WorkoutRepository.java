package uni.mainz.TrainingsTracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import uni.mainz.TrainingsTracker.dto.WorkoutRequest;
import uni.mainz.TrainingsTracker.dto.WorkoutResponse;
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
        return jdbcClient
                .sql("select * from workout")
                .query(WorkoutResponse.class)
                .list();
    }

    public Optional<WorkoutResponse> getById(int id) {
        return jdbcClient
                .sql("select * from workout where id = :id")
                .param("id", id)
                .query(WorkoutResponse.class)
                .optional();
    }

    public List<WorkoutResponse> getByParams(Date date) {
        return jdbcClient
                .sql("select * from workout where workout.date = :date")
                .param("date", date)
                .query(WorkoutResponse.class)
                .list();
    }

    public List<WorkoutResponse> getByParams(WorkoutType type) {
        LOGGER.info(type.toString());
        return jdbcClient
                .sql("select * from workout where workout.type = :type")
                .param("type", type.toString())
                .query(WorkoutResponse.class)
                .list();
    }

    public List<WorkoutResponse> getByParams(Date date, WorkoutType workoutType) {

        return jdbcClient
                .sql("select * from workout where workout.date = ? and workout.type = ?")
                .params(List.of(date, workoutType.toString()))
                .query(WorkoutResponse.class)
                .list();
    }

    public int create(WorkoutRequest workoutRequest) {
        return 1;
    }

    public int update(WorkoutRequest workoutRequest, Integer id) {
        return jdbcClient
                .sql("update workout set date = ?, type = ? where id = ?")
                .params(List.of(workoutRequest.date(), workoutRequest.type().toString(), id))
                .update();
    }

    public int delete(int id) {
        return 1;
    }

}
