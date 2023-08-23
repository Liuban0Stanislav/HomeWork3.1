package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        logger.debug("Вызван контроллер FacultyService");
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Вызван метод createFaculty");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(Long id) {
        logger.debug("Вызван метод findFaculty");
        return facultyRepository.findById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Вызван метод editFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.debug("Вызван метод deleteFaculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("Вызван метод getAllFaculties");
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultyAccordingName(String name) {
        logger.debug("Вызван метод getFacultyAccordingName");
        return facultyRepository.findFacultyByNameContainingIgnoreCase(name);
    }

    public List<Faculty> getFacultyAccordingColor(String color) {
        logger.debug("Вызван метод getFacultyAccordingName");
        return facultyRepository.findFacultyByColorContainsIgnoreCase(color);
    }

    public Faculty findFacultyByStudent(Student student) {
        logger.debug("Вызван метод findFacultyByStudent");
        return facultyRepository.findFacultyByStudent(student);
    }

    public List<Faculty> findFacultyByNameAndColor(String name, String color) {
        logger.debug("Вызван метод findFacultyByNameAndColor");
        return facultyRepository.findFacultyByNameContainingIgnoreCaseAndColorContainingIgnoreCase(name, color);
    }

    public Faculty getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .max(Comparator.comparing(faculty -> faculty.getName().length()))
                .orElse(null);
    }

    public int getIntegerNumber() {
        return Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }
}
