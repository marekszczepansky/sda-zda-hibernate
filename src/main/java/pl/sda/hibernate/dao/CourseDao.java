package pl.sda.hibernate.dao;


import pl.sda.hibernate.entity.Course;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {
    Course updateNameById(int id, String newName);

    List<Course> findByNameLike(String term);

}
