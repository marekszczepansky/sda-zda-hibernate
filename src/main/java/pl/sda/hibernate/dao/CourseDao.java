package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Course;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {
    List<Course> findByNameLike(final String nameTerm);
}
