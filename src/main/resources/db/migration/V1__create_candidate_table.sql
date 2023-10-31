CREATE TABLE candidate (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255),
                           surname VARCHAR(255),
                           middlename VARCHAR(255),
                           photo BYTEA,
                           description TEXT,
                           cv_file BYTEA
);