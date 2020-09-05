package pl.sda.hibernate.di;

import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.DefaultFoodDao;
import pl.sda.hibernate.dao.DefaultIngredientDao;
import pl.sda.hibernate.dao.DefaultPlaceDao;
import pl.sda.hibernate.dao.FoodDao;
import pl.sda.hibernate.dao.IngredientDao;
import pl.sda.hibernate.dao.PlaceDao;
import pl.sda.hibernate.services.BootstrapService;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static Context instance = new Context();
    private static Map<String, Object> componentStore = new HashMap<>();

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

    public BootstrapService getBootstrapService() {
        return (BootstrapService) componentStore
                .computeIfAbsent("BootstrapService", s -> new BootstrapService(
                        getIngredientDao(), getFoodDao(), getPlaceDao()
                ));
    }
}
