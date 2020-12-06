package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Teacher;

import java.util.List;

public interface TeacherDao extends BaseDao<Teacher> {
    List<Teacher> getAllTeachersForCourseId(final int id);
}
