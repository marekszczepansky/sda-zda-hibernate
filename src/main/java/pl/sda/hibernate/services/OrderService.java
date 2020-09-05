package pl.sda.hibernate.services;

import pl.sda.hibernate.dao.FoodDao;
import pl.sda.hibernate.dao.OrderDao;
import pl.sda.hibernate.dao.PlaceDao;
import pl.sda.hibernate.entity.Order;

import java.time.LocalDateTime;

public class OrderService {
    private final PlaceDao placeDao;
    private final FoodDao foodDao;
    private final OrderDao orderDao;

    public OrderService(PlaceDao placeDao, FoodDao foodDao, OrderDao orderDao) {
        this.placeDao = placeDao;
        this.foodDao = foodDao;
        this.orderDao = orderDao;
    }

    public void exampleOrder1() {
            Order order = new Order();
            order.setCustomerFirstName("Marek");
            order.setCustomerPhoneNumber("+48 505 12 34 65");
            order.setOrderTime(LocalDateTime.now());
            order.setPickUpTime(LocalDateTime.now().plusMinutes(30));
            order.setPlace(placeDao.findById(1));
            order.getFoods().add(foodDao.findById(1));
            orderDao.create(order);
        }
}
