package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Student;

import java.util.List;
import java.util.Set;

public interface StudentDao {
    void create(Student student);
    void create(Set<Student> students);
    Student findById(final int id);
    List<Student> findAllByCourseId(final int id);
}
