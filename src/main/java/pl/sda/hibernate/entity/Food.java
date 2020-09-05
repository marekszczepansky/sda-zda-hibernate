package pl.sda.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Food extends BaseEntity{
    private String name;
    private String description;
    private double price;
    @ManyToMany
    private Set<Ingredient> ingredients = new HashSet<>();
    @ManyToMany(mappedBy = "foods")
    private Set<Order> orders = new HashSet<>();

    public Set<Order> getOrders() {
        return orders;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Food food = (Food) o;
        return Double.compare(food.price, price) == 0 &&
                Objects.equals(name, food.name) &&
                Objects.equals(description, food.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price);
    }
}
