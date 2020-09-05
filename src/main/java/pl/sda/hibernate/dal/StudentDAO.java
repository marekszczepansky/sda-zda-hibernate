package pl.sda.hibernate.dal;

import pl.sda.hibernate.entity.Student;

import java.util.List;

public interface StudentDAO {
    void create(List<Student> students);
}
