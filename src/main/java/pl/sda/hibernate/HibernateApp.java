package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateApp {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")

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

    }

    private static void findCourseById(int id) {

    }

    private static void findCourseByIdAndUpdate(int id) {

    }

    private static void findCourseByNameLike(String term) {

    }

    private static void createStudentsForCourseId(int id) {

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

    private static String getOpenInfo(){
        return String.format("\n<-----------\n-= Method %s called =-\n", Thread.currentThread().getStackTrace()[2].getMethodName());
    }
    private static String getCloseInfo(){
        return String.format("\n-= Method %s finished =-", Thread.currentThread().getStackTrace()[2].getMethodName());
    }


}
