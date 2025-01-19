package uni.mainz.TrainingsTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uni.mainz.TrainingsTracker.dto.SetDTO;
import uni.mainz.TrainingsTracker.dto.TrainingRequest;
import uni.mainz.TrainingsTracker.dto.TrainingResponse;
import uni.mainz.TrainingsTracker.dto.WorkoutResponse;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.model.Training;
import uni.mainz.TrainingsTracker.repository.TrainingRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    // Should be handled in a Service Object
    private TrainingResponse singleTrainingToTrainingResponse(List<Training> trainings) {
        Training first = trainings.getFirst();
        WorkoutResponse workout = new WorkoutResponse(first.workoutId(), first.date(), first.type());

        Map<String, List<Training>> exerciseTrainingMap = trainings
                .stream()
                .collect(Collectors.groupingBy(t -> t.exerciseName() ));
        Map<String, List<SetDTO>> exercises = exerciseTrainingMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(t -> new SetDTO(t.setOrderNumber(), t.setWeight(), t.setRepetitions()))
                                .collect(Collectors.toList())));

        return new TrainingResponse(workout, exercises);

    }

    @GetMapping("")
    public List<TrainingResponse> getAll() {
        List<Training> trainings = trainingRepository.getAll();
        return trainings
                .stream()
                .collect(Collectors.groupingBy(Training::workoutId))
                .values()
                .stream()
                .map(this::singleTrainingToTrainingResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TrainingResponse getById(@PathVariable int id) {
        List<Training> result = trainingRepository.getById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Exercise", String.valueOf(id));
        }
        return singleTrainingToTrainingResponse(result);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody TrainingRequest trainingRequest) {}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody TrainingRequest trainingRequest, @PathVariable int id) {}

    @DeleteMapping("/{id}")
    public void delete(@RequestParam int id) {}
}
