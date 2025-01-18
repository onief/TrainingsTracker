CREATE TABLE workout (
    id SERIAL PRIMARY KEY,
    "date" DATE NOT NULL,
    "type" VARCHAR(255) NOT NULL
);

CREATE TABLE exercise (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description TEXT,
    pre_specified BOOLEAN DEFAULT FALSE
);

CREATE TABLE workout_exercise (
    id SERIAL PRIMARY KEY,
    workout INTEGER REFERENCES workout(id),
    exercise INTEGER REFERENCES exercise(id)
);

CREATE TABLE set (
    id SERIAL PRIMARY KEY,
    order_number INTEGER NOT NULL,
    weight INTEGER NOT NULL DEFAULT 0,
    repetitions INTEGER NOT NULL,
    workout_exercise INTEGER REFERENCES workout_exercise(id)
);