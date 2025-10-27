package com.demo.order.repository;

import com.demo.order.model.Order;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class OrderRepository {
    private final Map<String, Order> storage = new ConcurrentHashMap<>();

    public Order createOrder(String customer, double amount) {
        String id = UUID.randomUUID().toString();
        Order order = new Order(id, customer, amount);
        storage.put(id, order);
        return order;
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Order> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void save(Order order) {
        storage.put(order.id, order);
    }

    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }

    // Pour tests/demo uniquement
    public void clear() {
        storage.clear();
    }
}
