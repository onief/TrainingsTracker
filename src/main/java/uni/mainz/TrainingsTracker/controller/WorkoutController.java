package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.model.WorkoutResponse;
import uni.mainz.TrainingsTracker.repository.WorkoutRepository;

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

    @GetMapping("")
    public List<WorkoutResponse> getAll() {
        return workoutRepository.getAll();
    }

    @GetMapping("/{id}")
    public WorkoutResponse getById(@PathVariable int id) {
        Optional<WorkoutResponse> result = workoutRepository.getById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Workout", String.valueOf(id));
        }
        return result.get();
    }

    // Inheritance? Controller and Repository superclass?
}
