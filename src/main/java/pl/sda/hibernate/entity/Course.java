package pl.sda.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends NamedEntity {

    private LocalDate startDate;

    @OneToMany(mappedBy = "course")
    private Set<Student> students = new HashSet<>();

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Set<Student> getStudents() {
        return students;
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
