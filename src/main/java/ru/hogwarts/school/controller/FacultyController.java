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
    /**
     * FacultyService field
     */
    private final FacultyService facultyService;

    /**
     * The constructor with FacultyService injection
     */
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    /**
     * Endpoint: Creating faculty.
     *
     * @The method takes a faculty object as a parameter.
     * @And also returns faculty object to use of an HTTP client.
     */
    @PostMapping //CREATE  http://localhost:8080/faculty
    public Faculty createStudent(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    /**
     * Endpoint: Searching faculty by id.
     *
     * @The method takes an id of a faculty as a parameter.
     * @The ResponseEntity returns status 200 and a faculty object if
     * faculty with such id was found, and it returns status 404 if
     * a student was not found.
     */
    @GetMapping("{id}") //READ  http://localhost:8080/faculty/1
    public ResponseEntity<Faculty> findFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id).orElse(null);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    /**
     * Endpoint: editing faculty.
     *
     * @The method takes a Faculty object as a parameter.
     * @The ResponseEntity returns status 200 and a faculty object if faculty was changed.
     */
    @PutMapping //UPDATE  http://localhost:8080/faculty
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editiedFaculty = facultyService.editFaculty(faculty);
        if (editiedFaculty == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    /**
     * Endpoint: delete faculty.
     *
     * @The method takes an id of a faculty as a parameter to delete someone.
     * @If method is completed, the ResponseEntity gives a status of 200.
     * @If method is not completed than it gives standard status of 500.
     */
    @DeleteMapping("{id}") //DELETE  http://localhost:8080/faculty/1
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint: getAllFaculties.
     *
     * @There is no parameters to takes by method.
     * @The method returns HTTP status of 200 and a collection of all faculties objects.
     */
    @GetMapping //READ  http://localhost:8080/faculty
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    /**
     * Endpoint: The filter of all faculties according their names or colors.
     *
     * @The method takes two parameters: name (String), or color (String).
     *If both parameters are given to the method: name and color, then the search will be
     * conducted by name. You can also search only by color, in which case the name parameter
     * must be null.
     * @The method returns a faculty objects collection containing faculties only
     * with name or color matching to the params.
     */
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

    /**
     * Endpoint: the filter of faculty according to their student.
     *
     * @The method request student object as a param to looking faculty.
     * @The method returns a faculty from same student.
     */
    @GetMapping("/find_faculty_by_student")
    public Faculty findFacultyByStudent(@RequestBody Student student) {
        return facultyService.findFacultyByStudent(student);
    }

    /**Endpoint: The method looking a faculty by name and color.
     * The method needs two params, and returns ResponseEntity with
     * collection of faculties inside.*/
    @GetMapping("/find_by_name_color")
    public ResponseEntity<List<Faculty>> findFacultyByNameAndColor(@RequestParam String name,
                                                                   @RequestParam String color) {
        List<Faculty> faculties = facultyService.findFacultyByNameAndColor(name, color);
        return ResponseEntity.ok(faculties);
    }
}
