CREATE TABLE analysis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_created DATETIME NOT NULL,
    analyzed TEXT NOT NULL,
    forecast VARCHAR(100) NOT NULL,
    probability FLOAT NOT NULL
);

CREATE TABLE errors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_created DATETIME NOT NULL,
    log_level VARCHAR(100) NOT NULL,
    class_name VARCHAR(255) NOT NULL,
    error_msg TEXT NOT NULL,
    stack_trace TEXT NOT NULL
);