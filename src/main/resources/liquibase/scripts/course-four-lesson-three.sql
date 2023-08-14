-- liquibase formatted sql

-- changeset slyuban:1
create index students_name_index on student (name);