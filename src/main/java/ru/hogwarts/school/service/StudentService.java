package ru.hogwarts.school.service;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.FiveLastStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository) {
        logger.debug("Вызван конструктор StudentService");
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("Вызван метод createStudent");
        return studentRepository.save(student);
    }

    public Optional<Student> findStudent(Long id) {
        logger.debug("Вызван метод findStudent");
        return studentRepository.findById(id);
    }

    public Student editStudent(Student student) {
        logger.debug("Вызван метод editStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.debug("Вызван метод deleteStudent");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.debug("Вызван метод getAllStudents");
        return studentRepository.findAll();
    }

    public List<Student> getStudentsAccordingAge(int age) {
        logger.debug("Вызван метод getStudentsAccordingAge");
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> findStudentByAgeBetween(int minAge, int maxAge) {
        logger.debug("Вызван метод findStudentByAgeBetween");
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public List<Student> findStudentByFaculty(Faculty faculty) {
        logger.debug("Вызван метод findStudentByFaculty");
        return studentRepository.findStudentByFaculty(faculty);
    }

    public List<Integer> getQuantityOfAllStudents() {
        logger.debug("Вызван метод getQuantityOfAllStudents");
        return studentRepository.getQuantityOfAllStudents();
    }

    public List<Double> getAverageAge() {
        logger.debug("Вызван метод getAverageAge");
        return studentRepository.getAverageAge();
    }

    public List<FiveLastStudents> getFiveLastStudents() {
        logger.debug("Вызван метод getFiveLastStudents");
        return studentRepository.getFiveLastStudents();
    }

    public List<Student> findStudentByName(String name) {
        logger.debug("Вызван метод findStudentByName");
        return studentRepository.findStudentByNameContainingIgnoreCase(name);
    }

    public List<Student> getStudentsAlphabetOrder() {
        logger.debug("Вызван метод getStudentsAlphabetOrder");
        return studentRepository.findAll().stream()
                .map(student -> new Student(student.getId(),
                        StringUtils.capitalize(student.getName()),
                        student.getAge()))
                .filter(student -> student.getName().startsWith("А"))
                .sorted(Comparator.comparing(student -> student.getName()))
                .collect(Collectors.toList());
    }

    public double getMiddleAgeOfStudents() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0d);
    }
}
