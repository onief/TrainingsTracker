package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uni.mainz.TrainingsTracker.exception.ConflictException;
import uni.mainz.TrainingsTracker.exception.PreSpecifiedException;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.dto.ExerciseRequest;
import uni.mainz.TrainingsTracker.dto.ExerciseResponse;
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

    private void throwResponses(int repositoryResponse, String identifier) {
        if (repositoryResponse == 1) {
            throw new NotFoundException("Exercise", identifier);
        }
        if (repositoryResponse == 2) {
            throw new PreSpecifiedException();
        }
        if (repositoryResponse == 3) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, identifier + " is referenced by a Training and cannot be deleted.");
        }
    }

    @GetMapping("")
    public List<ExerciseResponse> getAll() {
        return exerciseRepository.getAll();
    }

    @GetMapping("/{identifier}")
    public ExerciseResponse getByName(@PathVariable String identifier) {

        Optional<ExerciseResponse> result = exerciseRepository.getByName(identifier);

        if (result.isEmpty()) {
            throw new NotFoundException("Exercise", identifier);
        }
        return result.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody ExerciseRequest exercise) {
        if (exerciseRepository.create(exercise) > 0) {
            throw new ConflictException("Exercise", exercise.name());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{identifier}")
    public void update(@RequestBody ExerciseRequest exercise, @PathVariable String identifier) {
        int repositoryResponse = exerciseRepository.update(exercise, identifier);
        throwResponses(repositoryResponse, identifier);
    }

    @DeleteMapping("/{identifier}")
    public void delete(@PathVariable String identifier) {
        int repositoryResponse = exerciseRepository.delete(identifier);
        throwResponses(repositoryResponse, identifier);
    }
}
