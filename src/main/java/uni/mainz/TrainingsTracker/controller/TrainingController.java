package uni.mainz.TrainingsTracker.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uni.mainz.TrainingsTracker.dto.*;
import uni.mainz.TrainingsTracker.exception.NotFoundException;
import uni.mainz.TrainingsTracker.model.Training;
import uni.mainz.TrainingsTracker.model.WorkoutType;
import uni.mainz.TrainingsTracker.repository.ExerciseRepository;
import uni.mainz.TrainingsTracker.repository.TrainingRepository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;

    public TrainingController(TrainingRepository trainingRepository, ExerciseRepository exerciseRepository) {
        this.trainingRepository = trainingRepository;
        this.exerciseRepository = exerciseRepository;
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

    @GetMapping("/{id}")
    public TrainingResponse getById(@PathVariable int id) {
        List<Training> result = trainingRepository.getById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Exercise", String.valueOf(id));
        }
        return singleTrainingToTrainingResponse(result);
    }

    @GetMapping("")
    public List<TrainingResponse> getByParams(@RequestParam(value="date", required=false) Date date, @RequestParam(value="type", required=false) WorkoutType type) {
        List<Training> trainings;

        if (date == null && type == null) {
            trainings = trainingRepository.getAll();
        } else if (date == null && type != null) {
            trainings = trainingRepository.getByParams(type);
        } else if (date != null && type == null) {
            trainings = trainingRepository.getByParams(date);
        } else {
            trainings = trainingRepository.getByParams(date, type);
        }

        return trainings
                .stream()
                .collect(Collectors.groupingBy(Training::workoutId))
                .values()
                .stream()
                .map(this::singleTrainingToTrainingResponse)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Validated @RequestBody TrainingRequest trainingRequest) {
        Map<String, List<SetDTO>> exercises = trainingRequest.exercises();

        // Data Integrity should be handled in a Service Object
        for (String exerciseId : exercises.keySet()) {
            if (exerciseRepository.getByName(exerciseId).isEmpty()) {
                throw new NotFoundException("Exercise", exerciseId);
            }
        }
        for (List<SetDTO> sets : exercises.values()) {
            List<Integer> order = sets.stream()
                    .map(SetDTO::order_number)
                    .sorted()
                    .toList();
            if (order.getFirst() != 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First Set must be 1.");
            }
            for (int i=0; i < order.size()-1; i++) {
                if (order.get(i+1) - order.get(i) != 1) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sets must be ordered ascending with diff of 1.");
                }
            }
        }

        trainingRepository.create(trainingRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody @Valid TrainingRequest trainingRequest, @PathVariable int id) {}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        if (trainingRepository.delete(id) == 1) {
            throw new NotFoundException("Workout", String.valueOf(id));
        }
    }
}
