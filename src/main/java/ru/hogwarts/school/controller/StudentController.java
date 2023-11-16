package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.FiveLastStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Endpoint: Creating student.
     *
     * @The method takes a student object as a parameter.
     * @And also returns student object to use of an HTTP client.
     */
    @PostMapping //CREATE  http://localhost:8080/student
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    /**
     * Endpoint: Searching student by id.
     *
     * @The method takes an id of a student as a parameter.
     * @The ResponseEntity returns status 200 and a student object if
     * student with such id was found, and it returns status 404 if
     * a student was not found.
     */
    @GetMapping("{id}") //READ  http://localhost:8080/student/1
    public ResponseEntity<Student> findStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    /**
     * Endpoint: editing student.
     *
     * @The method takes a Student object as a parameter.
     * @The ResponseEntity returns status 200 and a student object if student was changed.
     */
    @PutMapping //UPDATE  http://localhost:8080/student/
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student edittingStudent = studentService.editStudent(student);
        if (edittingStudent == null) {
            ResponseEntity.notFound().build();
        }
        System.out.println("ResponseEntity " + ResponseEntity.ok(student));
        return ResponseEntity.ok(student);
    }

    /**
     * Endpoint: delete student.
     *
     * @The method takes an id of a student as a parameter to delete someone.
     * @If method is completed, the ResponseEntity gives a status of 200.
     * @If method is not completed than it gives standard status of 500.
     */
    @DeleteMapping("{id}") //DELETE  http://localhost:8080/student/1
    public ResponseEntity<Student> deleteStudent(@RequestBody @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint: getAllStudents.
     *
     * @There is no parameters to takes by method.
     * @The method returns HTTP status of 200 and a collection of all student objects.
     */
    @GetMapping //READ  http://localhost:8080/student
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Endpoint: The filter of all students according their ages.
     *
     * @The method takes an age parameter.
     * @The method returns a student objects collection containing students only
     * with age matching an age param.
     */
    @GetMapping("/filter_by_age/{age}") //READ  http://localhost:8080/student/filter_by_age/20
    public List<Student> getStudentsAccordingAge(@PathVariable int age) {
        return studentService.getStudentsAccordingAge(age);
    }

    /**
     * Endpoint: the filter looking students according age limits.
     *
     * @The method request two int params: min and max age. These define search limits.
     * @The method returns a collection of students with ages between limits.
     */
    @GetMapping("/find_age_between")
    public List<Student> findStudentByAgeBetween(@RequestParam int minAge,
                                                 @RequestParam int maxAge) {
        return studentService.findStudentByAgeBetween(minAge, maxAge);
    }

    /**
     * Endpoint: the filter of students according to their faculty.
     *
     * @The method request faculty object as a param to looking students.
     * @The method returns a collection of students from same faculty.
     */
    @GetMapping("/find_student_by_faculty")
    public List<Student> findStudentByFaculty(@RequestBody Faculty faculty) {
        return studentService.findStudentByFaculty(faculty);
    }

    /**
     * Endpoint: the method returns a number of a quantity of
     * all students totally from all faculties.
     *
     * @The method doesn't take any params, and returns Integer number.
     */
    @GetMapping("/get_quantity_of_all_students")
    public List<Integer> getQuantityOfAllStudents() {
        return studentService.getQuantityOfAllStudents();
    }

    /**
     * Endpoint: the calculation of the average age from all students from all faculties.
     *
     * @There is no params to pass into method.
     * @And the method returns Double number.
     */
    @GetMapping("/get_average_age")
    public List<Double> getAverageAge() {
        return studentService.getAverageAge();
    }

    /**
     * Endpoint: the method gives five last students from database.
     *
     * @The method returns collection from five students.
     */
    @GetMapping("/get_five_last_students")
    public List<FiveLastStudents> getFiveLastStudents() {
        return studentService.getFiveLastStudents();
    }

    /**
     * Endpoint: the method looking students by their name.
     *
     * @The method takes as a param String "name" which we are looking.
     * @The method returns ResponseEntity with collection of students inside ones.
     * In case of method work successfully, ResponseEntity return status of 200.
     */
    @GetMapping("/find_by_name/{name}")
    public ResponseEntity<List<Student>> findStudentByAge(@PathVariable String name) {
        List<Student> students = studentService.findStudentByName(name);
        return ResponseEntity.ok(students);
    }

    /**
     * Endpoint: the method capitalise first letters of student's names,
     * then looking all students whose name start from "A",
     * and then sort them in alphabetic order. Collection of sorted
     * students returns from method.
     */
    @GetMapping("/get_students_alphabetic_order")
    public Collection<Student> getStudentAlphabeticOrder() {
        return studentService.getStudentsAlphabetOrder();
    }

    /**
     * Endpoint: the method returns average age of students using StreamAPI.
     */
    @GetMapping("/get_students_middle_age")
    public double getMiddleAgeOfStudents() {
        return studentService.getMiddleAgeOfStudents();
    }

    /**
     * The method contains two threads just to see order of executions methods into each thread.
     */
    @GetMapping("/do_student_thread")
    public void doStudentsThread() {
        studentService.doStudentsThread();
    }

    /**
     * The method contains two threads with thread synchronization.
     */
    @GetMapping("/do_synchronized_student_thread")
    public void doSynchronizedStudentsThread() {
        studentService.doSynchronizedStudentsThread();
    }
}
