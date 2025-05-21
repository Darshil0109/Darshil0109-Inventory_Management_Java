CREATE DATABASE inventory;
\c inventory;

CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    price DECIMAL(10,2),
    quantity INTEGER
); 