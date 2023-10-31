CREATE TABLE test_specialization (
                                     test_id BIGINT,
                                     specialization_id BIGINT,
                                     PRIMARY KEY (test_id, specialization_id),
                                     FOREIGN KEY (test_id) REFERENCES test (id),
                                     FOREIGN KEY (specialization_id) REFERENCES specialization (id)
);
