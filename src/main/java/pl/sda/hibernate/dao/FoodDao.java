package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Food;

public interface FoodDao {
    void create(Food food);

    Food findById(int id);
}
