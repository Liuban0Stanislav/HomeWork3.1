package ru.hogwarts.school.entity;
/**
 * This interface is needed for use in the StudentRepository.getFiveLastStudents().
 * This entity contains student data returned from databases.
 * Because you need to return not all information about
 * the student, but only them and the name.*/
public interface FiveLastStudents {
    Integer getId();
    String getName();
}
