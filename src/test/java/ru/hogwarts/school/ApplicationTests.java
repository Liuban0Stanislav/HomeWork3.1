package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Student student;
    private static Faculty faculty;
    private static Long studentTestId;
    private static Long facultyTestId;

//    @BeforeAll
//    static void setUp() {
//
//
//    }

    @Test
    void contextLoads() throws Exception {
        assertNotNull(studentController);
        assertNotNull(facultyController);
    }

    @Test
    void testPostStudent() {
        student = new Student(1L, "Василий Теркин", 23);

        assertNotNull(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/student", student, String.class));

    }

    @Test
    void testPostFaculty() {
        faculty = new Faculty(1L, "Грипенкашль", "малиновый");

        assertNotNull(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/faculty", faculty, String.class));
    }

    @Test
    void findStudent() {
        assertNotNull(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/student/1", String.class));
    }

    @Test
    void findFaculty() {
        assertNotNull(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/faculty/1", String.class));
    }

    @Test
    public void testEditStudent() {
        List<Student>lastStudent = studentRepository.findAll();
        Long lastId = (long) lastStudent.size();

        Student student = new Student(lastId, "Александр Матросов", 19);
        Assertions.assertThat(this.testRestTemplate.postForObject(
                        "http://localhost:" + port + "/faculty/" + lastId, student, Student.class))
                .isEqualTo(student);
    }

//    @Test
//    public void testEditFaculty() {
//        Faculty faculty = new Faculty(1L, "Пуфендуй", "синий");
//
//        ResponseEntity<Faculty> response = facultyController.editFaculty(faculty);
//
//        int actualStatusCodeValue = response.getStatusCodeValue();
//        int expectedCode = 200;
//
//        Assertions.assertEquals(expectedCode, actualStatusCodeValue, "коды не совпадают");
//    }

    @Test
    void deleteStudentTest() {

    }
}
