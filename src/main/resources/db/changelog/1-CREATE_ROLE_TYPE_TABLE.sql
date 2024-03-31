-- liquibase formatted sql

-- changeset HOUSING:1
-- validCheckSum: 1:any
-- preconditions onFail:MARK_RAN onError:HALT
-- preconditions-sql-check expectedResult:0 select count(*) from pg_type where typname = 'role'

CREATE TYPE role AS ENUM ('ADMIN', 'MANAGER', 'AGENT', 'TENANT', 'LANDLORD')