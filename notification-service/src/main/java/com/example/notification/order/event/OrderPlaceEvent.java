package com.example.notification.order.event;

import java.util.Objects;

public class OrderPlaceEvent {

    private String orderNumber;
    private String email;

    public OrderPlaceEvent() {
    }

    public OrderPlaceEvent(String orderNumber, String email) {
        this.orderNumber = orderNumber;
        this.email = email;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPlaceEvent that = (OrderPlaceEvent) o;
        return Objects.equals(orderNumber, that.orderNumber) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, email);
    }

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderNumber='" + orderNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
