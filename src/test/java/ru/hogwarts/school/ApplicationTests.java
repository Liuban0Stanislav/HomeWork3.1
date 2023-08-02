package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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


import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private int port;

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

    @BeforeAll
    static void setUp() {
        student = new Student(1L, "Василий Теркин", 23);
        faculty = new Faculty(1L, "Грипенкашль", "малиновый");
    }

    @Test
    void contextLoads() throws Exception {
        assertNotNull(studentController);
        assertNotNull(facultyController);
    }

    @Test
    void testPostStudent() throws Exception {
        assertNotNull(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/student", student, String.class));

    }

    @Test
    void testPostFaculty() throws Exception {
        assertNotNull(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/faculty", faculty, String.class));

    }

    @Test
    public void testEditStudent() throws URISyntaxException {
        Student student = new Student(1L, "Андрей Пивоваров", 25);

        ResponseEntity<Student> response = studentController.editStudent(student);

//
//        int actualStatusCodeValue = response.getStatusCodeValue();
//        int expectedCode = 200;
//
//        Assertions.assertEquals(expectedCode, actualStatusCodeValue, "коды не совпадают");

        Long idChange = 2L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Changing mathod", "done");

        URI uri = new URI("http://localhost:" + port + "/student");
        RequestEntity<URI> requestEntity = RequestEntity.post(uri);
testRestTemplate.exchange((RequestEntity<?>) RequestEntity.post(uri), Student.class);
//        assertNotNull(this.testRestTemplate.exchange("http://localhost:" + port + "/student" + idChange,
//                HttpMethod.PUT,
//                new HttpEntity<>(headers),
//                Student.class));
    }

    @Test
    public void testEditFacuty() {
        Faculty faculty = new Faculty(1L, "Пуфендуй", "синий");

        ResponseEntity<Faculty> response = facultyController.editFaculty(faculty);

        int actualStatusCodeValue = response.getStatusCodeValue();
        int expectedCode = 200;

        Assertions.assertEquals(expectedCode, actualStatusCodeValue, "коды не совпадают");
    }

    @Test
    void deleteStudentTest() {

    }
}
