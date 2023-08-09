package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Entity(name = "Faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    @OneToMany (mappedBy = "faculty")
    @JsonIgnore
    private Collection<Student> student;

    public Faculty() {
    }

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Collection<Student> getStudent() {
        return student;
    }

    public void setStudent(Collection<Student> student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id.equals(faculty.id) && name.equals(faculty.name) && color.equals(faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "название факультета:" + name + '\n' +
                "цвет факультета: " + color + '\n';
    }
}
