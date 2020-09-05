package pl.sda.hibernate.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "FoodOrder")
public class Order extends BaseEntity{
    private LocalDateTime orderTime;
    private String customerPhoneNumber;
    private LocalDateTime pickUpTime;
    private String customerFirstName;
    @ManyToMany
    private Set<Food> foods = new HashSet<>();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Place place;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public LocalDateTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(LocalDateTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(orderTime, order.orderTime) &&
                Objects.equals(customerPhoneNumber, order.customerPhoneNumber) &&
                Objects.equals(pickUpTime, order.pickUpTime) &&
                Objects.equals(customerFirstName, order.customerFirstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderTime, customerPhoneNumber, pickUpTime, customerFirstName);
    }
}
