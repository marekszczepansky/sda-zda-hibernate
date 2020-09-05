package pl.sda.hibernate.di;

import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.DefaultFoodDao;
import pl.sda.hibernate.dao.DefaultIngredientDao;
import pl.sda.hibernate.dao.DefaultOrderDao;
import pl.sda.hibernate.dao.DefaultPlaceDao;
import pl.sda.hibernate.dao.FoodDao;
import pl.sda.hibernate.dao.IngredientDao;
import pl.sda.hibernate.dao.OrderDao;
import pl.sda.hibernate.dao.PlaceDao;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {
    private static Context instance = new Context();
    private static Map<String, Object> componentStore = new ConcurrentHashMap<>();

    private Context() {
    }

    public static Context getInstance() {
        return instance;
    }

    public FoodDao getFoodDao() {
        return (FoodDao) componentStore.
                computeIfAbsent("FoodDao", s -> new DefaultFoodDao(HibernateConfiguration.getInstance()));
    }

    public IngredientDao getIngredientDao() {
        return (IngredientDao) componentStore
                .computeIfAbsent("IngredientDao", s -> new DefaultIngredientDao(HibernateConfiguration.getInstance()));
    }

    public PlaceDao getPlaceDao() {
        return (PlaceDao) componentStore
                .computeIfAbsent("PlaceDao", s -> new DefaultPlaceDao(HibernateConfiguration.getInstance()));
    }

    public OrderDao getOrderDao(){
        return (OrderDao) componentStore
                .computeIfAbsent("OrderDao", s -> new DefaultOrderDao(HibernateConfiguration.getInstance()));
    }

    public BootstrapService getBootstrapService() {
        return (BootstrapService) componentStore
                .computeIfAbsent("BootstrapService", s -> new BootstrapService(
                        getIngredientDao(), getFoodDao(), getPlaceDao()
                ));
    }

    public OrderService getOrderService(){
        return (OrderService) componentStore
                .computeIfAbsent("OrderService", s -> new OrderService(
                        getPlaceDao(), getFoodDao(), getOrderDao()
                ));
    }
}
