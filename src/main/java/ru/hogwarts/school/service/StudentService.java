package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.FiveLastStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> findStudent(Long id) {
        return studentRepository.findById(id);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsAccordingAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findStudentByAgeBetween(int minAge, int maxAge){
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public List<Student> findStudentByFaculty(Faculty faculty){
        return studentRepository.findStudentByFaculty(faculty);
    }

    public Integer getQuantityOfAllStudents() {
        return studentRepository.getQuantityOfAllStudents();
    }

    public Double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public Collection<FiveLastStudents> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }

    public List<Student> findStudentByName(String name) {
        return studentRepository.findStudentByNameContainingIgnoreCase(name);
    }
}
