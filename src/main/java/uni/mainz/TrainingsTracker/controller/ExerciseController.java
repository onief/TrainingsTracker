package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uni.mainz.TrainingsTracker.model.ExerciseRequest;
import uni.mainz.TrainingsTracker.model.ExerciseResponse;
import uni.mainz.TrainingsTracker.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    private Optional<ExerciseResponse> getExerciseByIdentifier(String identifier) {
        Optional<ExerciseResponse> result;

        if (identifier.matches("\\d+")) {
            int id = Integer.parseInt(identifier);
            result = exerciseRepository.getById(id);
        } else {
            result = exerciseRepository.getByName(identifier);
        }

        return result;
    }

    @GetMapping("")
    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.getAll();
    }

    @GetMapping("/{identifier}")
    public ExerciseResponse getById(@PathVariable String identifier) {

        Optional<ExerciseResponse> result = getExerciseByIdentifier(identifier);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found.");
        }
        return result.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody ExerciseRequest exercise) {

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{identifier}")
    public void update(@RequestBody ExerciseRequest exercise, @PathVariable String identifier) {

    }

    @DeleteMapping("/{identifier}")
    public void delete(@PathVariable String identifier) {
        exerciseRepository.delete(identifier);
    }
}
