package pl.sda.hibernate;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.di.Context;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.OrderService;

public class HibernateApp {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {

        BootstrapService bootstrapService = Context.getInstance().getBootstrapService();
        OrderService orderService = Context.getInstance().getOrderService();

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        bootstrapService.createIngredients();
        bootstrapService.createFoods();
        bootstrapService.createPlaces();

        orderService.exampleOrder1();
    }
}
