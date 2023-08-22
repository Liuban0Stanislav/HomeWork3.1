package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNull;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    private Student expectedStudent;

    private final List<Student> sourceStudentsList = new ArrayList<>(List.of(
            new Student(1L, "аd_testStudent4", 45),
            new Student(2L, "аb_testStudent2", 45),
            new Student(3L, "аc_testStudent3", 45),
            new Student(4L, "аa_testStudent1", 45)
    ));

    @BeforeEach
    public void BeforeEach(){
        studentService = new StudentService(studentRepository);
        expectedStudent = new Student(1L,"Boris", 35);
        studentService.createStudent(expectedStudent);
    }

    @Test
    public void createStudentTest() {
        Mockito.when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        assertEquals(expectedStudent, studentService.createStudent(expectedStudent));
    }

    @Test
    public void findStudentTest(){
        Optional<Student> optionalStudent = Optional.ofNullable(expectedStudent);
        Mockito.when(studentRepository.findById(1L)).thenReturn(optionalStudent);

        assertEquals(expectedStudent.getName(), studentService.findStudent(1L).get().getName());
        assertEquals(expectedStudent.getAge(), studentService.findStudent(1L).get().getAge());
    }

    @Test
    public void editStudentTest(){
        Optional<Student> optionalStudent = Optional.ofNullable(expectedStudent);
        Mockito.when(studentRepository.findById(1L)).thenReturn(optionalStudent);

        assertEquals(expectedStudent.getName(), studentService.findStudent(1L).get().getName());
        assertEquals(expectedStudent.getAge(), studentService.findStudent(1L).get().getAge());

        Student newStudent = new Student(2L,"Karl", 31);
        Optional<Student> optionalStudent1 = Optional.ofNullable(newStudent);
        studentService.editStudent(newStudent);

        Mockito.when(studentRepository.findById(1L)).thenReturn(optionalStudent1);

        assertEquals(newStudent.getName(), studentService.findStudent(1L).get().getName());
        assertEquals(newStudent.getAge(), studentService.findStudent(1L).get().getAge());
    }

    @Test
    public void deleteStudentTest(){
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAllStudentsTest(){
        List<Student>students = new ArrayList<>();
        Student student1 = new Student(1L, "1111", 1);
        students.add(student1);
        Student student2 = new Student(2L, "2222", 2);
        students.add(student2);

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        assertTrue(studentService.getAllStudents().containsAll(students));
    }

    @Test
    public void getStudentsAccordingAgeTest(){

        studentService.createStudent(new Student(2L, "Rik", 25));
        studentService.createStudent(new Student(3L, "Bik", 15));
        studentService.createStudent(new Student(4L, "Mik", 25));

        List<Student>expextedList1 = new ArrayList<>(List.of(
                new Student(3L, "Bik", 15)
        ));

        Mockito.when(studentRepository.findStudentByAge(15)).thenReturn(expextedList1);
        assertEquals(expextedList1, studentService.getStudentsAccordingAge(15));

        List<Student>expextedList2 = new ArrayList<>(List.of(
                new Student(2L, "Rik", 25),
                new Student(4L, "Mik", 25)
        ));

        Mockito.when(studentRepository.findStudentByAge(25)).thenReturn(expextedList2);
        assertEquals(expextedList2, studentService.getStudentsAccordingAge(25));
    }

    @Test
    public void getStudentsAlphabetOrderTest() {
        List<Student> expectedStudentsList = new ArrayList<>(List.of(
                new Student(4L, "Аa_testStudent1", 45),
                new Student(2L, "Аb_testStudent2", 45),
                new Student(3L, "Аc_testStudent3", 45),
                new Student(1L, "Аd_testStudent4", 45)
        ));

        when(studentRepository.findAll()).thenReturn(sourceStudentsList);

        assertEquals(expectedStudentsList, studentService.getStudentsAlphabetOrder());
    }

    @Test
    public void getMiddleAgeOfStudents() {
        when(studentRepository.findAll()).thenReturn(sourceStudentsList);

        assertEquals(45, studentService.getMiddleAgeOfStudents());
    }
}
