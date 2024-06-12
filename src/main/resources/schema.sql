DROP TABLE IF EXISTS motorcycles;

CREATE TABLE motorcycles (
    brand VARCHAR(64) NOT NULL PRIMARY KEY,
    model VARCHAR(64) NOT NULL,
    price DOUBLE NOT NULL,
    category VARCHAR(64) NOT NULL,
    image VARCHAR(64) NOT NULL
);
