package pl.sda.hibernate.services;

import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BootstrapService {
    public static final int COURSE_ID = 1;
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    public BootstrapService(CourseDao courseDao, StudentDao studentDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
    }

    public void createCourses() {
        Set<Course> courses = new HashSet<>();

        Course course = new Course();
        course.setName("course 1");
        course.setStartDate(LocalDate.of(2021, 2, 24));
        courses.add(course);
        System.out.println("course 1 persisted");

        course = new Course();
        course.setName("course 2");
        course.setStartDate(LocalDate.of(2021, 3, 12));
        courses.add(course);
        System.out.println("course 2 persisted");

        course = new Course();
        course.setName("course 3");
        course.setStartDate(LocalDate.of(2021, 1, 6));
        courses.add(course);
        System.out.println("course 3 persisted");
        courseDao.create(courses);

    }

    public void createStudents() {
        Set<Student> students = new HashSet<>();
        final Course course = courseDao.findById(COURSE_ID);

        Student student = new Student();
        student.setName("Natalia");
        student.setEmail("natalia@sda.pl");
        student.setCourse(course);
        students.add(student);
        System.out.printf("Student %s created for course %s\n", student.getName(), course.getName());

        student = new Student();
        student.setName("Marek");
        student.setEmail("marek@sda.pl");
        student.setCourse(course);
        students.add(student);
        System.out.printf("Student %s created for course %s\n", student.getName(), course.getName());

        student = new Student();
        student.setName("Kacper");
        student.setEmail("kacper@sda.pl");
        student.setCourse(course);
        students.add(student);
        System.out.printf("Student %s created for course %s\n", student.getName(), course.getName());

        studentDao.create(students);
    }

    public void createTeachers() {
        Set<Teacher> teachers = new HashSet<>();
        final Course course = courseDao.findById(COURSE_ID);

        Teacher teacher = new Teacher();
        teacher.setName("Wojtek");
        teacher.setSubject("Java podstawy");
        teacher.getCourses().add(course);
        teachers.add(teacher);
        System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());

        teacher = new Teacher();
        teacher.setName("Franek");
        teacher.setSubject("Bazy danych");
        teacher.getCourses().add(course);
        teachers.add(teacher);
        System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());

        teacher = new Teacher();
        teacher.setName("Artur");
        teacher.setSubject("SQL");
        teacher.getCourses().add(course);
        teachers.add(teacher);
        System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());

        teacher = new Teacher();
        teacher.setName("Eryk");
        teacher.setSubject("Spring");
        teacher.getCourses().add(course);
        teachers.add(teacher);
        System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());
        teacherDao.create(teachers);
    }
}
