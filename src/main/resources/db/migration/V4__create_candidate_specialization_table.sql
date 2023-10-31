CREATE TABLE candidate_specialization (
                                          candidate_id BIGINT,
                                          specialization_id BIGINT,
                                          PRIMARY KEY (candidate_id, specialization_id),
                                          FOREIGN KEY (candidate_id) REFERENCES candidate (id),
                                          FOREIGN KEY (specialization_id) REFERENCES specialization (id)
);
