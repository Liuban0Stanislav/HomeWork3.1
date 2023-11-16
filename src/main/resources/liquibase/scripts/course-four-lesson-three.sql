-- liquibase formatted sql

-- changeset slyuban:1
create index students_name_index on student (name);

-- changeset slyuban:2
create index faculty_name_and_color_index on faculty (name, color);