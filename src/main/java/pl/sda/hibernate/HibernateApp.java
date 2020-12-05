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
            course.setStartDate(LocalDate.of(2021, 2, 24));
            session.persist(course);
            System.out.println("course 1 persisted");

            course = new Course();
            course.setName("course 2");
            course.setStartDate(LocalDate.of(2021, 3, 12));
            session.persist(course);
            System.out.println("course 2 persisted");

            course = new Course();
            course.setName("course 3");
            course.setStartDate(LocalDate.of(2021, 1, 6));
            session.persist(course);
            System.out.println("course 3 persisted");

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseById(final int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("\nCourse with id %d found:\n%s\n", id, course);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
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
            System.out.printf("\nCourse with id %d found:\n%s\n", id, course);

            course.setName("course name updated");
            tx.commit();
            session.evict(course);
            tx = session.beginTransaction();
            course.setName("course after evict");

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseByNameLike(final String term) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Query<Course> courseQuery = session.createQuery(
                    "from Course c where name like :nameparam",
                    Course.class);
            courseQuery.setParameter("nameparam", term);
            final List<Course> courseList = courseQuery.getResultList();
            System.out.printf("Query for courses with name like %s\n", term);
            courseList.forEach(course -> System.out.printf("course found: %s\n", course));

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void createStudentsForCourseId(final int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            final Course course = session.find(Course.class, id);

            Student student = new Student();
            student.setName("Natalia");
            student.setEmail("natalia@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student %s created for course %s\n", student.getName(), course.getName());

            student = new Student();
            student.setName("Marek");
            student.setEmail("marek@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student %s created for course %s\n", student.getName(), course.getName());

            student = new Student();
            student.setName("Kacper");
            student.setEmail("kacper@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student %s created for course %s\n", student.getName(), course.getName());

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
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
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static String getOpenInfo(){
        return String.format("\n<-----------\n-= Method %s called =-\n", Thread.currentThread().getStackTrace()[2].getMethodName());
    }
    private static String getCloseInfo(){
        return String.format("\n-= Method %s finished =-", Thread.currentThread().getStackTrace()[2].getMethodName());
    }


}
