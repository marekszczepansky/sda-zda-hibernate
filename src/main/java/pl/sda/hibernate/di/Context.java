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
import java.util.function.Supplier;

public class Context {
    private static Context instance = new Context();
    private static Map<Class<?>, Object> componentStore = new ConcurrentHashMap<>();
    // dependency map (via supplies)
    private Map<Class<?>, Supplier<?>> componentSuppliers = new HashMap<>();

    private Context() {
        registerComponents();
    }

    public static Context getInstance() {
        return instance;
    }

    private void registerComponents() {
        this.componentSuppliers.put(HibernateConfiguration.class, HibernateConfiguration::new);
        this.componentSuppliers.put(FoodDao.class, () -> new DefaultFoodDao(
                getComponent(HibernateConfiguration.class)
        ));
        this.componentSuppliers.put(IngredientDao.class, () -> new DefaultIngredientDao(
                getComponent(HibernateConfiguration.class)
        ));
        this.componentSuppliers.put(PlaceDao.class, () -> new DefaultPlaceDao(
                getComponent(HibernateConfiguration.class)
        ));
        this.componentSuppliers.put(OrderDao.class, () -> new DefaultOrderDao(
                getComponent(HibernateConfiguration.class)
        ));
        this.componentSuppliers.put(BootstrapService.class, () -> new BootstrapService(
                getComponent(IngredientDao.class),
                getComponent(FoodDao.class),
                getComponent(PlaceDao.class)
        ));
        this.componentSuppliers.put(OrderService.class, () -> new OrderService(
                getComponent(PlaceDao.class),
                getComponent(FoodDao.class),
                getComponent(OrderDao.class)
        ));
    }

    public <K> K getComponent(Class<K> key) {
        return (K) componentStore.computeIfAbsent(key, aClass -> componentSuppliers.get(aClass).get());
    }
}
