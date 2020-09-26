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
import java.util.List;
import java.util.Set;

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

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        courseDao = new HibernateCourseDao(sessionFactory);
        studentDao = new HibernateStudentDao(sessionFactory);

        createCourses();
        findCourseById(1);
        findCourseByIdAndUpdate(2);
        findCourseByNameLike("course%");
        createStudentsForCourseId(1);
        getAllStudentsForCourse(1);
        createTeachersForCourse(1);
        getAllTeachersForCourse(1);
    }

    private static void createCourses() {
        System.out.println(getOpenInfo());
        Course course = new Course();

        course.setName("course 1");
        course.setStartDate(LocalDate.of(2020, 3, 8));
        courseDao.create(course);

        course = new Course();
        course.setName("course 2");
        course.setStartDate(LocalDate.of(2020, 4, 12));
        courseDao.create(course);

        course = new Course();
        course.setName("course 3");
        course.setStartDate(LocalDate.of(2020, 4, 29));
        courseDao.create(course);

        System.out.println(getCloseInfo());
    }

    private static void findCourseById(int id) {
        System.out.println(getOpenInfo());

        final Course course = courseDao.findById(id);
        System.out.printf("course with id %d foud: %s\n%s\n", id, course.getName(), course);

        System.out.println(getCloseInfo());
    }

    private static void findCourseByIdAndUpdate(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("course with id %d foud: %s\n%s\n", id, course.getName(), course);
            course.setName("course name updated");
            tx.commit();
            session.evict(course);
            tx = session.beginTransaction();
            course.setName("updated in new transaction after evict");
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseByNameLike(String term) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Query<Course> courseQuery = session.createQuery(
                    "select c from Course c where name like :nameparam",
                    Course.class);
            courseQuery.setParameter("nameparam", term);
            final List<Course> resultList = courseQuery.getResultList();
            resultList.forEach(courseItem -> System.out.println("course found: " + courseItem));

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void createStudentsForCourseId(int id) {
        System.out.println(getOpenInfo());

        final Course course = courseDao.findById(id);

        Student student = new Student();
        student.setName("Emil");
        student.setEmail("emil@sda.pl");
        student.setCourse(course);
        studentDao.create(student);
        System.out.println("Student Emil is persisted");

        student = new Student();
        student.setName("Beata");
        student.setEmail("beata@sda.pl");
        student.setCourse(course);
        studentDao.create(student);
        System.out.println("Student Beata i spersisted");

        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourse(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            final Set<Student> students = course.getStudents();
            System.out.println("course.getStudents called");
            System.out.println("Number of students: " + students.size());
            students.forEach(studentItem -> System.out.println("student name: " + studentItem.getName()));

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void createTeachersForCourse(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);

            Teacher teacher = new Teacher();
            teacher.setName("Wojtek");
            teacher.setSubject("Java");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.println("Teacher Wojtek is persisted");

            teacher = new Teacher();
            teacher.setName("Joanna");
            teacher.setSubject("Angular");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.println("Teacher Joanna is persisted");

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void getAllTeachersForCourse(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            final Set<Teacher> teachers = course.getTeachers();
            System.out.println("Teacher list:");
            teachers.forEach(
                    teacherItem -> System.out.println("teacher name: " + teacherItem.getName())
            );

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
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
