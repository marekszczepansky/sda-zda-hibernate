package pl.sda.hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.hibernate.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByCourseId(int id);
}
