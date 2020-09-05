package pl.sda.hibernate.services;

import pl.sda.hibernate.dao.FoodDao;
import pl.sda.hibernate.dao.IngredientDao;
import pl.sda.hibernate.dao.PlaceDao;
import pl.sda.hibernate.entity.Food;
import pl.sda.hibernate.entity.Ingredient;
import pl.sda.hibernate.entity.Place;

import java.util.Set;

public class BootstrapService {
    private final IngredientDao ingredientDao;
    private final FoodDao foodDao;
    private final PlaceDao placeDao;

    public BootstrapService(IngredientDao ingredientDao, FoodDao foodDao, PlaceDao placeDao) {
        this.ingredientDao = ingredientDao;
        this.foodDao = foodDao;
        this.placeDao = placeDao;
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

        ingredient = new Ingredient();
        ingredient.setName("Jalapenio");
        ingredient.setKcal(60);
        ingredientDao.create(ingredient);
    }

    public void createFoods() {
        Food food = new Food();
        Set<Ingredient> ingredients;

        food.setName("Burger amerykański");
        food.setDescription("Bardzo dobry, polecany przez prawdziwych Jankesów :)");
        food.setPrice(23);
        ingredients = food.getIngredients();
        ingredients.add(ingredientDao.findById(1));
        ingredients.add(ingredientDao.findById(2));
        ingredients.add(ingredientDao.findById(3));
        ingredients.add(ingredientDao.findById(4));
        foodDao.create(food);

        food = new Food();
        food.setName("Hot&Spicy");
        food.setDescription("Coś dla twardzieli");
        food.setPrice(25);
        ingredients = food.getIngredients();
        ingredients.add(ingredientDao.findById(1));
        ingredients.add(ingredientDao.findById(2));
        ingredients.add(ingredientDao.findById(3));
        ingredients.add(ingredientDao.findById(5));
        foodDao.create(food);
    }

    public void createPlaces(){
        Place place = new Place();

        place.setName("Stary Rynek");
        place.setAddress("ul. Wielka 12");
        place.setPhoneNumber("+48 505 999 854");
        placeDao.create(place);

        place = new Place();
        place.setName("Piatkowo");
        place.setAddress("os. Stefana Batorego 44");
        place.setPhoneNumber("+48 505 999 855");
        placeDao.create(place);
    }
}
