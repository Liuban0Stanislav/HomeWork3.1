package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping //CREATE  http://localhost:8080/student
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @GetMapping("{id}") //READ  http://localhost:8080/student/1
    public ResponseEntity<Student> findStudent(@RequestBody @PathVariable Long id){
        Student student = studentService.findStudent(id);
        if(student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @PutMapping("{id}") //UPDATE  http://localhost:8080/student/1
    public ResponseEntity<Student> editStudent(@RequestBody Student student){
        Student edittingStudent = studentService.editStudent(student);
        if(edittingStudent == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @DeleteMapping("{id}") //DELETE  http://localhost:8080/student/1
    public ResponseEntity<Student> deleteStudent(@RequestBody @PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping //READ  http://localhost:8080/student
    public ResponseEntity<Collection<Student>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping("/filter_by_age/{age}") //READ  http://localhost:8080/student/filter_by_age/20
    public List<Student> getStudentsAccordingAge(@PathVariable int age){
        return studentService.getStudentsAccordingAge(age);
    }

    @GetMapping("/find_age_between")
    public List<Student> findStudentByAgeBetween(@RequestParam int minAge,
                                                 @RequestParam int maxAge){
        return studentService.findStudentByAgeBetween(minAge, maxAge);
    }
    @GetMapping("/find_student_by_faculty")
    public List<Student> findStudentByFaculty(@RequestBody Faculty faculty){
        return studentService.findStudentByFaculty(faculty);
    }
}
