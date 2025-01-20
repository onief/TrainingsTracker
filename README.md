# TrainingsTracker
RestAPI using Spring Boot to track Trainings Data

## Endpoints

### `/api/exercise`
API to interact with the Exercises (Examples in: api/exercise.http )
- **GET:** Get all Exercises **/** Get Excercise by Name (`/name`)
- **POST:** Create Exercise with DTO described by ExerciseRequest
- **PUT:** Update Exercise with DTO described by ExerciseRequest (`/name`)
- **DELETE:** Delete Exercise by Name (`/name`)

### `/api/workout`

### `/api/training`

## Improvements
- Add Inheritance Hierarchy for Controller and Repository
- Remove Redundancy of JDBC queries
- Add Service Layer for Trainings
- Add Put Method for TrainingsAPI
