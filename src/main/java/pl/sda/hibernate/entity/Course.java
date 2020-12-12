package pl.sda.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course_table")
public class Course extends NamedEntity {

    @Column(nullable = false)
    private LocalDate startDate;
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private final Set<Student> students = new HashSet<>();
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private final Set<Teacher> teachers = new HashSet<>();

    public Course() {
    }

    public Course(int courseId, String courseName, Date courseStartDate) {
        this.id = courseId;
        this.name = courseName;
        this.startDate = courseStartDate.toLocalDate();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return Objects.equals(startDate, course.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
