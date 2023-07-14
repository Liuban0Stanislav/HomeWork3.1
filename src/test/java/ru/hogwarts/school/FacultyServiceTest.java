package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    private FacultyService facultyService;
    private Faculty expectedFaculty;

    @BeforeEach
    public void BeforeEach(){
        facultyService = new FacultyService();
        expectedFaculty = new Faculty(1L,"Грипенкашль", "желтый");
        facultyService.createFaculty(expectedFaculty);
    }
    @Test
    public void createFacultyTest() {
        assertEquals(expectedFaculty, facultyService.createFaculty(expectedFaculty));
        assertTrue(facultyService.getAllFaculties().contains(expectedFaculty));
    }
    @Test
    public void findFacultyTest(){
        assertEquals(expectedFaculty.getName(), facultyService.findFaculty(1L).getName());
        assertEquals(expectedFaculty.getColor(), facultyService.findFaculty(1L).getColor());
    }

    @Test
    public void editFacultyTest(){
        assertEquals(expectedFaculty.getName(), facultyService.findFaculty(1L).getName());
        assertEquals(expectedFaculty.getColor(), facultyService.findFaculty(1L).getColor());

        Faculty newFaculty = new Faculty(1L,"Пупкодуй", "синий");
        facultyService.editFaculty(newFaculty);

        assertEquals(newFaculty.getName(), facultyService.findFaculty(1L).getName());
        assertEquals(newFaculty.getColor(), facultyService.findFaculty(1L).getColor());
    }
    @Test
    public void deleteStudentTest(){
        facultyService.deleteFaculty(1L);
        assertTrue(facultyService.getAllFaculties().isEmpty());
    }

    @Test
    public void getFacultiesAccordingAgeTest(){
        facultyService.createFaculty(new Faculty(2L, "1111", "синий"));
        facultyService.createFaculty(new Faculty(3L, "2222", "зеленый"));
        facultyService.createFaculty(new Faculty(4L, "3333", "зеленый"));

        List<Faculty> expectedList1 = new ArrayList<>(List.of(
                new Faculty(2L, "1111", "синий")
        ));
        assertEquals(expectedList1, facultyService.getFacultyAccordingColor("синий"));

        List<Faculty>expectedList2 = new ArrayList<>(List.of(
                new Faculty(3L, "2222", "зеленый"),
                new Faculty(4L, "3333", "зеленый")
        ));
        assertEquals(expectedList2, facultyService.getFacultyAccordingColor("зеленый"));
    }
}
