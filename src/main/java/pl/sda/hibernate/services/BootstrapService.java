package pl.sda.hibernate.services;

import pl.sda.hibernate.dao.DefaultFoodDao;
import pl.sda.hibernate.dao.DefaultIngredientDao;
import pl.sda.hibernate.entity.Food;
import pl.sda.hibernate.entity.Ingredient;

public class BootstrapService {
    private final DefaultIngredientDao ingredientDao;
    private final DefaultFoodDao foodDao;

    public BootstrapService(DefaultIngredientDao ingredientDao, DefaultFoodDao foodDao) {
        this.ingredientDao = ingredientDao;
        this.foodDao = foodDao;
    }

    public void createIngredients() {
        Ingredient ingredient = new Ingredient();

        ingredient.setName("Sałata");
        ingredient.setKcal(50);
        ingredientDao.create(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Pomidor");
        ingredient.setKcal(80);
        ingredientDao.create(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Wołowina");
        ingredient.setKcal(200);
        ingredientDao.create(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Bekon");
        ingredient.setKcal(100);
        ingredientDao.create(ingredient);
    }

    public void createFoods() {
        Food food = new Food();

        food.setName("Burger amerykański");
        food.setDescription("Bardzo dobry, polecany przez prawdziwych Jankesów :)");
        food.setPrice(23);
        foodDao.create(food);

        food = new Food();
        food.setName("Hot&Spicy");
        food.setDescription("Coś dla twardzieli");
        food.setPrice(25);
        foodDao.create(food);

    }
}