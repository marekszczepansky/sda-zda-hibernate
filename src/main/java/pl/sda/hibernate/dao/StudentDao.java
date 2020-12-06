package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {
    List<Student> findAllByCourseId(final int id);
}
