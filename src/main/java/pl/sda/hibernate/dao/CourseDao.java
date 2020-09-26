package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Course;

public interface CourseDao {
    void create(Course course);
    Course findById(int id);
}
