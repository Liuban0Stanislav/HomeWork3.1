package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    private FacultyService facultyService;
    @Mock
    private FacultyRepository facultyRepository;
    private Faculty expectedFaculty;

    @BeforeEach
    public void BeforeEach(){
        facultyService = new FacultyService(facultyRepository);
        expectedFaculty = new Faculty(1L,"Грипенкашль", "желтый");
        facultyService.createFaculty(expectedFaculty);
    }
    @Test
    public void createFacultyTest() {
        Mockito.when(facultyRepository.save(expectedFaculty)).thenReturn(expectedFaculty);

        assertEquals(expectedFaculty, facultyService.createFaculty(expectedFaculty));
    }
    @Test
    public void findFacultyTest(){
        Mockito.when(facultyRepository.getById(1L)).thenReturn(expectedFaculty);

        assertEquals(expectedFaculty.getName(), facultyService.findFaculty(1L).getName());
        assertEquals(expectedFaculty.getColor(), facultyService.findFaculty(1L).getColor());
    }

    @Test
    public void editFacultyTest(){
        Mockito.when(facultyRepository.getById(1L)).thenReturn(expectedFaculty);

        assertEquals(expectedFaculty.getName(), facultyService.findFaculty(1L).getName());
        assertEquals(expectedFaculty.getColor(), facultyService.findFaculty(1L).getColor());

        Faculty newFaculty = new Faculty(1L,"Пупкодуй", "синий");
        facultyService.editFaculty(newFaculty);

        Mockito.when(facultyRepository.getById(1L)).thenReturn(newFaculty);

        assertEquals(newFaculty.getName(), facultyService.findFaculty(1L).getName());
        assertEquals(newFaculty.getColor(), facultyService.findFaculty(1L).getColor());
    }
    @Test
    public void deleteStudentTest(){
        facultyService.deleteFaculty(1L);
        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAllFacultiesTest(){
        List<Faculty>faculties = new ArrayList<>();
        Faculty faculty1 = new Faculty(1L, "1111", "red");
        faculties.add(faculty1);
        Faculty faculty2 = new Faculty(3L, "3333", "red");
        faculties.add(faculty2);

        Mockito.when(facultyRepository.findAll()).thenReturn(faculties);

        assertTrue(facultyService.getAllFaculties().containsAll(faculties));
    }

    @Test
    public void getFacultiesAccordingAgeTest(){
        facultyService.createFaculty(new Faculty(2L, "1111", "синий"));
        facultyService.createFaculty(new Faculty(3L, "2222", "зеленый"));
        facultyService.createFaculty(new Faculty(4L, "3333", "зеленый"));

        List<Faculty> expectedList1 = new ArrayList<>(List.of(
                new Faculty(2L, "1111", "синий")
        ));

        Mockito.when(facultyRepository.findByColor(anyString())).thenReturn(expectedList1);
        assertEquals(expectedList1, facultyService.getFacultyAccordingColor("синий"));

        List<Faculty>expectedList2 = new ArrayList<>(List.of(
                new Faculty(3L, "2222", "зеленый"),
                new Faculty(4L, "3333", "зеленый")
        ));

        Mockito.when(facultyRepository.findByColor(anyString())).thenReturn(expectedList2);
        assertEquals(expectedList2, facultyService.getFacultyAccordingColor("зеленый"));
    }
}
