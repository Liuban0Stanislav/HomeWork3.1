package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long lastStudentId = 0;

    @PostConstruct
    public void init() {
        Student student1 = new Student(1L,"Andrey", 20);
        student1.setId(++lastStudentId);
        studentMap.put(student1.getId(), student1);

        Student student2 = new Student(2L,"Alex", 20);
        student2.setId(++lastStudentId);
        studentMap.put(student2.getId(), student2);

        Student student3 = new Student(3L,"Andy", 22);
        student3.setId(++lastStudentId);
        studentMap.put(student3.getId(), student3);

        Student student4 = new Student(4L,"Anton", 19);
        student4.setId(++lastStudentId);
        studentMap.put(student4.getId(), student4);
    }

    public Student createStudent(Student student) {
        student.setId(++lastStudentId);
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student findStudent(Long id) {
        return studentMap.get(id);
    }

    public Student editStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        return studentMap.remove(id);
    }

    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }

    public List<Student> getStudentsAccordingAge(int age) {
        List<Student> studentList = studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        return studentList;
    }
}
