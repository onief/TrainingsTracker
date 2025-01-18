package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.mainz.TrainingsTracker.model.Exercise;
import uni.mainz.TrainingsTracker.repository.ExerciseRepository;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("")
    public List<Exercise> getAllExercises() {
        return exerciseRepository.getAllExercises();
    }

}
