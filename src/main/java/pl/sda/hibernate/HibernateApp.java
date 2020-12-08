package pl.sda.hibernate;

import pl.sda.hibernate.di.Context;
import pl.sda.hibernate.di.ContextMemory;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.SearchService;

public class HibernateApp {

    private static BootstrapService bootstrapService;
    private static SearchService searchService;

    public static void main(String[] args) {

        final ContextMemory context = ContextMemory.getInstance();

        bootstrapService = context.getComponent(BootstrapService.class);
        searchService = context.getComponent(SearchService.class);

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        bootstrapService.createCourses();
        bootstrapService.createStudents();
        bootstrapService.createTeachers();

        searchService.findCourseById(1);
        searchService.findCourseByNameLike("course%");
        searchService.getAllStudentsForCourseIdByQuery(1);
        searchService.getAllTeachersForCourse(1);
    }


}
