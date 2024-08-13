CREATE SCHEMA IF NOT EXISTS google_drive;

DROP TABLE IF EXISTS google_drive.user_info CASCADE;
DROP TABLE IF EXISTS google_drive.file_info CASCADE;

CREATE TABLE IF NOT EXISTS google_drive.user_info
(
    id       UUID PRIMARY KEY NOT NULL,
    email    VARCHAR UNIQUE   NOT NULL,
    password VARCHAR          NOT NULL
);

CREATE TABLE IF NOT EXISTS google_drive.file_info
(
    id UUID PRIMARY KEY NOT NULL,
    user_id UUID NOT NULL REFERENCES google_drive.user_info (id),
    name VARCHAR NOT NULL,
    path VARCHAR NOT NULL,
    extension VARCHAR NOT NULL,
    size FLOAT NOT NULL,
    type VARCHAR NOT NULL
);