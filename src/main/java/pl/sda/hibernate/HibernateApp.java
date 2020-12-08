package pl.sda.hibernate;

import org.springframework.context.ConfigurableApplicationContext;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.SearchService;

public class HibernateApp {

    private static BootstrapService bootstrapService;
    private static SearchService searchService;

    public static void mainOld(ConfigurableApplicationContext applicationContext) {


        bootstrapService = applicationContext.getBean(BootstrapService.class);
        searchService = applicationContext.getBean(SearchService.class);

        bootstrapService.createCourses();
        bootstrapService.createStudents();
        bootstrapService.createTeachers();

        searchService.findCourseById(1);
        searchService.findCourseByNameLike("course%");
        searchService.getAllStudentsForCourseIdByQuery(1);
        searchService.getAllTeachersForCourse(1);
    }


}
