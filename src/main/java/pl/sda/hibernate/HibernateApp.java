package pl.sda.hibernate;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.DefaultFoodDao;
import pl.sda.hibernate.dao.DefaultIngredientDao;
import pl.sda.hibernate.dao.DefaultPlaceDao;
import pl.sda.hibernate.dao.FoodDao;
import pl.sda.hibernate.dao.IngredientDao;
import pl.sda.hibernate.dao.PlaceDao;
import pl.sda.hibernate.services.BootstrapService;

public class HibernateApp {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {


        IngredientDao ingredientDao =
                new DefaultIngredientDao(HibernateConfiguration.getInstance());
        FoodDao foodDao =
                new DefaultFoodDao(HibernateConfiguration.getInstance());
        PlaceDao placeDao =
                new DefaultPlaceDao(HibernateConfiguration.getInstance());

        BootstrapService bootstrapService =
                new BootstrapService(ingredientDao, foodDao, placeDao);

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        bootstrapService.createIngredients();
        bootstrapService.createFoods();
        bootstrapService.createPlaces();

    }
}
