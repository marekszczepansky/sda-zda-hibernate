package pl.sda.hibernate.services;

import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;

import java.util.List;

public class SearchService {
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final Screen screen;


    public SearchService(CourseDao courseDao, StudentDao studentDao, TeacherDao teacherDao, Screen screen) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
        this.screen = screen;
    }

    public void findCourseById(final int id) {
        final Course course = courseDao.findById(id);
        screen.printf("\nCourse with id %d found:\n%s\n", id, course);
    }

    public void findCourseByNameLike(final String term) {
        final List<Course> courseList = courseDao.findByNameLike(term);
        screen.printf("Query for courses with name like %s\n", term);
        courseList.forEach(course -> screen.printf("course found: %s\n", course));
    }

    public void getAllStudentsForCourseIdByQuery(final int id) {
        final List<Student> studentList = studentDao.findAllByCourseId(id);
        screen.println("List of students for course Id: " + id);
        screen.println("Course name: " + studentList
                .stream()
                .findFirst()
                .map(Student::getCourse)
                .map(Course::getName)
                .orElse("<empty>")
        );
        studentList.forEach(student -> screen.printf(
                "Student name: %s, student email: %s\n",
                student.getName(),
                student.getEmail()
        ));
    }

    public void getAllTeachersForCourse(int id) {
        teacherDao.getAllTeachersForCourseId(id)
                .forEach(teacher -> screen.printf(
                        "Teacher name: %s, teacher subject: %s\n",
                        teacher.getName(),
                        teacher.getSubject())
                );
    }

}
