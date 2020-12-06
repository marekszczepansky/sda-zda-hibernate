package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Course;

import java.util.List;
import java.util.Set;

public interface CourseDao {
    void create(Course course);
    void create(Set<Course> courses);
    Course findById(final int id);
    List<Course> findByNameLike(final String nameTerm);
}
