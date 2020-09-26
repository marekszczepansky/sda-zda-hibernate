package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import java.time.LocalDate;
import java.util.List;

public class HibernateApp {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

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
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Course course = new Course();

            course.setName("course 1");
            course.setStartDate(LocalDate.of(2020, 3, 8));
            session.persist(course);

            course = new Course();
            course.setName("course 2");
            course.setStartDate(LocalDate.of(2020, 4, 12));
            session.persist(course);

            course = new Course();
            course.setName("course 3");
            course.setStartDate(LocalDate.of(2020, 4, 29));
            session.persist(course);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseById(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("course with id %d foud: %s\n%s\n", id, course.getName(), course);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
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
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);

            Student student = new Student();
            student.setName("Emil");
            student.setEmail("emil@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.println("Student Emil is persisted");

            student = new Student();
            student.setName("Beata");
            student.setEmail("beata@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.println("Student Beata i spersisted");

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourse(int id) {

    }

    private static void createTeachersForCourse(int id) {

    }

    private static void getAllTeachersForCourse(int id) {

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
