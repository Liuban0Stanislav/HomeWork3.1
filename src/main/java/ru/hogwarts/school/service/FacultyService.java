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
        logger.debug("Called method createFaculty");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(Long id) {
        logger.debug("Called method findFaculty");
        return facultyRepository.findById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Called method editFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.debug("Called method deleteFaculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("Called method getAllFaculties");
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultyAccordingName(String name) {
        logger.debug("Called method getFacultyAccordingName");
        return facultyRepository.findFacultyByNameContainingIgnoreCase(name);
    }

    public List<Faculty> getFacultyAccordingColor(String color) {
        logger.debug("Called method getFacultyAccordingName");
        return facultyRepository.findFacultyByColorContainsIgnoreCase(color);
    }

    public Faculty findFacultyByStudent(Student student) {
        logger.debug("Called method findFacultyByStudent");
        return facultyRepository.findFacultyByStudent(student);
    }

    public List<Faculty> findFacultyByNameAndColor(String name, String color) {
        logger.debug("Called method findFacultyByNameAndColor");
        return facultyRepository.findFacultyByNameContainingIgnoreCaseAndColorContainingIgnoreCase(name, color);
    }

    /**
     * The method returns the longest faculty name using StreamAPI.
     */
    public Faculty getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .max(Comparator.comparing(faculty -> faculty.getName().length()))
                .orElse(null);
    }

    /**
     * This method returns the sum of the first 1,000,000 natural numbers, 
     * starting at 1. This is done by creating a stream, limiting its size, 
     * and performing a reduction operation that adds up all the numbers in the stream. 
     * The result will be the sum of numbers from 1 to 1,000,000.
     */
    public int getIntegerNumber() {
        return Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }
}
