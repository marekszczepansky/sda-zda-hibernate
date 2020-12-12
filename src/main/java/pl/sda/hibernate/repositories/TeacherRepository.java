package pl.sda.hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.hibernate.entity.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository <Teacher, Integer> {
    List<Teacher> findByCoursesId(int id);
}
