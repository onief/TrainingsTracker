package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uni.mainz.TrainingsTracker.dto.TrainingRequest;
import uni.mainz.TrainingsTracker.dto.TrainingResponse;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.model.Training;
import uni.mainz.TrainingsTracker.repository.TrainingRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    private TrainingResponse toTrainingResponse(Training training) {
        return null;
    }

    @GetMapping("")
    public List<TrainingResponse> getAll() {
        return trainingRepository
                .getAll()
                .stream()
                .map(t -> toTrainingResponse(t))
                .toList();
    }

    @GetMapping("/{id}")
    public TrainingResponse getById(@RequestParam int id) {
        Optional<Training> result = trainingRepository.getById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Exercise", String.valueOf(id));
        }
        return toTrainingResponse(result.get());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody TrainingRequest trainingRequest) {}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody TrainingRequest trainingRequest, @RequestParam int id) {}

    @DeleteMapping("/{id}")
    public void delete(@RequestParam int id) {}
}
