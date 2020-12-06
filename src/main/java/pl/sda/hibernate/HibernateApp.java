package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.dao.HibernateCourseDao;
import pl.sda.hibernate.dao.HibernateStudentDao;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class HibernateApp {

    static SessionFactory sessionFactory;

    private static CourseDao courseDao;
    private static StudentDao studentDao;

    public static void main(String[] args) {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();

        courseDao = new HibernateCourseDao(sessionFactory);
        studentDao = new HibernateStudentDao(sessionFactory);

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        createCourses();
        findCourseById(1);
        findCourseByIdAndUpdate(2);
        findCourseByNameLike("course%");
        createStudentsForCourseId(1);
        getAllStudentsForCourseIdByQuery(1);
        getAllStudentsForCourseIdBySet(1);
        createTeachersForCourse(1);
        getAllTeachersForCourse(1);
    }

    private static void createCourses() {
        System.out.println(getOpenInfo());

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

        System.out.println(getCloseInfo());
    }

    private static void findCourseById(final int id) {
        System.out.println(getOpenInfo());

        final Course course = courseDao.findById(id);
        System.out.printf("\nCourse with id %d found:\n%s\n", id, course);

        System.out.println(getCloseInfo());
    }

    private static void findCourseByIdAndUpdate(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("\nCourse with id %d found:\n%s\n", id, course);

            course.setName("course name updated");
            tx.commit();
            session.evict(course);
            tx = session.beginTransaction();
            course.setName("course after evict");

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseByNameLike(final String term) {
        System.out.println(getOpenInfo());

        final List<Course> courseList = courseDao.findByNameLike(term);
        System.out.printf("Query for courses with name like %s\n", term);
        courseList.forEach(course -> System.out.printf("course found: %s\n", course));

        System.out.println(getCloseInfo());
    }

    private static void createStudentsForCourseId(final int id) {
        System.out.println(getOpenInfo());

        Set<Student> students = new HashSet<>();
        final Course course = courseDao.findById(id);

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

        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourseIdByQuery(final int id) {
        System.out.println(getOpenInfo());

            final List<Student> studentList = studentDao.findAllByCourseId(id);
            System.out.println("List of students for course Id: " + id);
            System.out.println("Course name: " + studentList
                    .stream()
                    .findFirst()
                    .map(Student::getCourse)
                    .map(Course::getName)
                    .orElse("<empty>")
            );
            studentList.forEach(student -> System.out.printf(
                    "Student name: %s, student email: %s\n",
                    student.getName(),
                    student.getEmail()
            ));
        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourseIdBySet(final int i) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Course course = session.find(Course.class, i);
            System.out.println("Students for course: " + course.getName());
            course.getStudents().forEach(student -> System.out.printf(
                    "Student name: %s, student email: %s\n",
                    student.getName(),
                    student.getEmail()
            ));
        });

        System.out.println(getCloseInfo());
    }

    private static void createTeachersForCourse(final int id) {
        System.out.println(getOpenInfo());
        doInTransaction(session -> {
            final Course course = session.find(Course.class, id);

            Teacher teacher = new Teacher();
            teacher.setName("Wojtek");
            teacher.setSubject("Java podstawy");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());

            teacher = new Teacher();
            teacher.setName("Franek");
            teacher.setSubject("Bazy danych");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());

            session.flush();  // testowe dodanie flush aby wymusić zapis do bazy

            teacher = new Teacher();
            teacher.setName("Artur");
            teacher.setSubject("SQL");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());

            teacher = new Teacher();
            teacher.setName("Eryk");
            teacher.setSubject("Spring");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher %s created with course %s\n", teacher.getName(), course.getName());
        });
        System.out.println(getCloseInfo());
    }

    private static void getAllTeachersForCourse(int id) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            System.out.println("Teachers for course: " + course.getName());
            course.getTeachers().forEach(teacher -> System.out.printf(
                    "Teacher name: %s, teacher subject: %s\n",
                    teacher.getName(),
                    teacher.getSubject()
            ));
        });

        System.out.println(getCloseInfo());
    }

    private static void doInTransaction(Consumer<Session> consumer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            consumer.accept(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    private static void template() {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            // body here

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static String getOpenInfo() {
        return String.format("\n<-----------\n-= Method %s called =-\n", Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private static String getCloseInfo() {
        return String.format("\n-= Method %s finished =-", Thread.currentThread().getStackTrace()[2].getMethodName());
    }


}
