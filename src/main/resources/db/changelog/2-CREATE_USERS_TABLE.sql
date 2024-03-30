-- liquibase formatted sql

-- changeset HOUSING:2
-- validCheckSum: 1:any
-- preconditions onFail:MARK_RAN onError:HALT
-- preconditions-sql-check expectedResult:0 SELECT count(*) FROM information_schema.tables where table_name = 'users'

CREATE TABLE users (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(increment by 1 start 1 minvalue 1 cache 1),
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    firstname VARCHAR(100),
    lastname varchar(100),
    active BOOLEAN NOT NULL DEFAULT FALSE,
    user_role role,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(30) NOT NULL DEFAULT '',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(30) NOT NULL DEFAULT '',
    CONSTRAINT uc_user_username UNIQUE (username)
);