CREATE TABLE candidate_test (
                                id SERIAL PRIMARY KEY,
                                candidate_id BIGINT,
                                test_id BIGINT,
                                local_date DATE,
                                mark INT,
                                FOREIGN KEY (candidate_id) REFERENCES candidate (id),
                                FOREIGN KEY (test_id) REFERENCES test (id)
);
