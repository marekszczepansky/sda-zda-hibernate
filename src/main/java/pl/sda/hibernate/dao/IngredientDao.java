package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Ingredient;

public interface IngredientDao {

    void create(Ingredient ingredient);

    Ingredient findById(int id);
}
