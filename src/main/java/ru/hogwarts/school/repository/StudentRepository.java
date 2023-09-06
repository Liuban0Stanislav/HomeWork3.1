package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.entity.FiveLastStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    List<Student> findStudentByAge(int age);
    List<Student> findStudentByAgeBetween(int minAge, int maxAge);
    List<Student> findStudentByFaculty(Faculty faculty);
    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getQuantityOfAllStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "select * from student order by id desc limit 5;", nativeQuery = true)
    List<FiveLastStudents> getFiveLastStudents();

    List<Student> findStudentByNameContainingIgnoreCase(String name);
}
