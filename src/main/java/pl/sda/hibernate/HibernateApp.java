package pl.sda.hibernate;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.DefaultFoodDao;
import pl.sda.hibernate.dao.DefaultIngredientDao;
import pl.sda.hibernate.services.BootstrapService;

public class HibernateApp {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {


        DefaultIngredientDao ingredientDao =
                new DefaultIngredientDao(HibernateConfiguration.getInstance());
        DefaultFoodDao foodDao =
                new DefaultFoodDao(HibernateConfiguration.getInstance());
        BootstrapService bootstrapService =
                new BootstrapService(ingredientDao, foodDao);

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        bootstrapService.createIngredients();
        bootstrapService.createFoods();

    }

    private static String getOpenInfo() {
        return String.format("\n<-----------\n-= Method %s called =-\n", Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private static String getCloseInfo() {
        return String.format("\n-= Method %s finished =-", Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
