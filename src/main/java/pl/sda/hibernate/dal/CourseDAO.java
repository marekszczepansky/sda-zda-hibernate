package pl.sda.hibernate.dal;

import pl.sda.hibernate.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    void create(Course course);
    void create(List<Course> courses);
    Optional<Course> findById(int id);
    List<Course> findByNameLike(String term);
}
