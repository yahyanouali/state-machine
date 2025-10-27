package com.demo.order.domain.status;

import com.demo.order.BiTransitionTo;
import com.demo.order.model.Order;

import java.util.function.Supplier;

public final class Purchased implements OrderStatus, BiTransitionTo<Shipped, Failed> {
    @Override
    public void onEnter(Order order) {
        order.log("Commande payée");
    }
    @Override
    public Shipped transitionToFirst(Supplier<Shipped> s) { return s.get(); }
    @Override
    public Failed transitionToSecond(Supplier<Failed> s) { return s.get(); }
}