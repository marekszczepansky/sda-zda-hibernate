package pl.sda.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TestCourse")
public class Course extends BaseEntity {

    @Column(nullable = false, length = 100, unique = true)
    private String name;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @OneToMany(mappedBy = "course")
    private Set<Student> students = new HashSet<>();
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Teacher> teachers = new HashSet<>();

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(startDate, course.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, startDate);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
