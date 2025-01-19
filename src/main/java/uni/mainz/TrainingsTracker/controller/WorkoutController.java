package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.dto.WorkoutRequest;
import uni.mainz.TrainingsTracker.dto.WorkoutResponse;
import uni.mainz.TrainingsTracker.model.WorkoutType;
import uni.mainz.TrainingsTracker.repository.WorkoutRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);
    private WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @GetMapping("/{id}")
    public WorkoutResponse getById(@PathVariable int id) {
        Optional<WorkoutResponse> result = workoutRepository.getById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Workout", String.valueOf(id));
        }
        return result.get();
    }

    @GetMapping("")
    public List<WorkoutResponse> getByParams(@RequestParam(value="date", required=false) Date date, @RequestParam(value="type", required=false) WorkoutType type) {
        if (date == null && type == null) {
            return workoutRepository.getAll();
        } else if (date == null && type != null) {
            return workoutRepository.getByParams(type);
        } else if (date != null && type == null) {
            return workoutRepository.getByParams(date);
        } else {
            return workoutRepository.getByParams(date, type);
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void create(@RequestBody WorkoutRequest workoutRequest) {}

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody WorkoutRequest workoutRequest, @PathVariable Integer id) {
        if (workoutRepository.update(workoutRequest, id) == 0) {
            throw new NotFoundException("Workout", String.valueOf(id));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void delete(@PathVariable int id) {}
}
