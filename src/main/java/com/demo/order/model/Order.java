package com.demo.order.model;

import com.demo.order.domain.status.OrderStatus;
import com.demo.order.domain.status.Pending;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public String id;
    public double amount;
    public String customer;
    public OrderStatus status;
    public List<String> history = new ArrayList<>();

    public Order(String id, String customer, double amount) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.status = new Pending();
        log("Commande créée");
        status.onEnter(this);
    }

    public void transitionTo(OrderStatus newStatus) {
        this.status = newStatus;
        newStatus.onEnter(this);
    }

    public void log(String entry) {
        history.add(status.getStatusName() + ": " + entry);
    }
}