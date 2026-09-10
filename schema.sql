DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    username varchar(255),
    password varchar(255),
    ip varchar(255),
    token varchar(255),
    latestLogin varchar(255)
)