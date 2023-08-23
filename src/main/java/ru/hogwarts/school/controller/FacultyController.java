package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping //CREATE  http://localhost:8080/faculty
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}") //READ  http://localhost:8080/faculty/1
    public ResponseEntity<Faculty> findFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id).orElse(null);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping //UPDATE  http://localhost:8080/faculty
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editiedFaculty = facultyService.editFaculty(faculty);
        if (editiedFaculty == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{id}") //DELETE  http://localhost:8080/faculty/1
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping //READ  http://localhost:8080/faculty
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("/filter_by_color") //READ  http://localhost:8080/faculty/filter_by_color
    public ResponseEntity getFacultyAccordingNameOrColor(@RequestParam(required = false, name = "name") String name,
                                                         @RequestParam(required = false, name = "color") String color) {
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyAccordingName(name));
        }
        if (color != null && !color.isEmpty() && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyAccordingColor(color));
        }
        return getAllFaculties();
    }

    @GetMapping("/find_faculty_by_student")
    public Faculty findFacultyByStudent(@RequestBody Student student) {
        return facultyService.findFacultyByStudent(student);
    }

    @GetMapping("/find_by_name_color")
    public ResponseEntity<List<Faculty>> findFacultyByNameAndColor(@RequestParam String name,
                                                                   @RequestParam String color) {
        List<Faculty> faculties = facultyService.findFacultyByNameAndColor(name, color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/get_longest_faculty_name")
    public Faculty getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
    }

    @GetMapping("/get_integer_number")
    public int getIntegerNumber() {
        return facultyService.getIntegerNumber();
    }
}
