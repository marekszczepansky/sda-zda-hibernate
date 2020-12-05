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
import java.util.function.Consumer;

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
        getAllStudentsForCourseIdByQuery(1);
        getAllStudentsForCourseIdBySet(1);
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

        doInTransaction(session -> {
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
        });

        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourseIdByQuery(final int id) {

        doInTransaction((Session session) -> {
            final Query<Student> studentQuery = session.createQuery(
                    "from Student s where s.course.id = :courseIdParam",
                    Student.class);
            studentQuery.setParameter("courseIdParam", id);
            final List<Student> studentList = studentQuery.getResultList();
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
        });

    }

    private static void getAllStudentsForCourseIdBySet(final int i) {
    }

    private static void createTeachersForCourse(int id) {

    }

    private static void getAllTeachersForCourse(int id) {

    }

    private static void doInTransaction(Consumer<Session> consumer){
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            consumer.accept(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
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
