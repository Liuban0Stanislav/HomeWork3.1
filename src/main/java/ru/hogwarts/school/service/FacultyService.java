package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long lastFacultyId = 0;

    @PostConstruct
    public void init() {
        Faculty faculty1 = new Faculty(1L,"Ковыряйнос", "салатовый");
        faculty1.setId(++lastFacultyId);
        facultyMap.put(faculty1.getId(), faculty1);

        Faculty faculty2 = new Faculty(2L,"Чешиспин", "красный");
        faculty2.setId(++lastFacultyId);
        facultyMap.put(faculty2.getId(), faculty2);

        Faculty faculty3 = new Faculty(3L,"Штанорван", "салатовый");
        faculty3.setId(++lastFacultyId);
        facultyMap.put(faculty3.getId(), faculty3);

        Faculty faculty4 = new Faculty(4L,"Коктейльпьин", "синий");
        faculty4.setId(++lastFacultyId);
        facultyMap.put(faculty4.getId(), faculty4);
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastFacultyId);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty findFaculty(Long id) {
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        return facultyMap.remove(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyMap.values();
    }

    public List<Faculty> getFacultyAccordingColor(String color) {
        List<Faculty> facultyList = facultyMap.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
        return facultyList;
    }
}
