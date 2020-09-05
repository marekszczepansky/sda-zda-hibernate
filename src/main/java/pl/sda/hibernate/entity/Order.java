package pl.sda.hibernate.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Order extends BaseEntity{
    private LocalDateTime orderTime;
    private String customerPhoneNumber;
    private LocalDateTime pickUpTime;
    private String customerFirstName;

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
