package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNull;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    private StudentService studentService;
    Student expectedStudent;

    @BeforeEach
    public void BeforeEach(){
        studentService = new StudentService();
        expectedStudent = new Student(1L,"Boris", 35);
        studentService.createStudent(expectedStudent);
    }

    @Test
    public void createStudentTest() {
        assertEquals(expectedStudent, studentService.createStudent(expectedStudent));
        assertTrue(studentService.getAllStudents().contains(expectedStudent));
    }

    @Test
    public void findStudentTest(){
        assertEquals(expectedStudent.getName(), studentService.findStudent(1L).getName());
        assertEquals(expectedStudent.getAge(), studentService.findStudent(1L).getAge());
    }

    @Test
    public void editStudentTest(){
        assertEquals(expectedStudent.getName(), studentService.findStudent(1L).getName());
        assertEquals(expectedStudent.getAge(), studentService.findStudent(1L).getAge());

        Student newStudent = new Student(1L,"Caesar", 33);
        studentService.editStudent(newStudent);

        assertEquals(newStudent.getName(), studentService.findStudent(1L).getName());
        assertEquals(newStudent.getAge(), studentService.findStudent(1L).getAge());
    }

    @Test
    public void deleteStudentTest(){
        studentService.deleteStudent(1L);
        assertTrue(studentService.getAllStudents().isEmpty());
    }

    @Test
    public void getAllStudentsTest(){
        studentService.createStudent(expectedStudent);
        assertTrue(studentService.getAllStudents().contains(expectedStudent));
    }

    @Test
    public void getStudentsAccordingAgeTest(){
        studentService.createStudent(new Student(2L, "Rik", 25));
        studentService.createStudent(new Student(3L, "Bik", 15));
        studentService.createStudent(new Student(4L, "Mik", 25));

        List<Student>expextedList1 = new ArrayList<>(List.of(
                new Student(3L, "Bik", 15)
        ));
        assertEquals(expextedList1, studentService.getStudentsAccordingAge(15));

        List<Student>expextedList2 = new ArrayList<>(List.of(
                new Student(2L, "Rik", 25),
                new Student(4L, "Mik", 25)
        ));
        assertEquals(expextedList2, studentService.getStudentsAccordingAge(25));
    }
}
