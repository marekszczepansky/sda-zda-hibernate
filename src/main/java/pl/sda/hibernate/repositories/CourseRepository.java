package pl.sda.hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.hibernate.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByNameLike(String nameTerm);
}
