package com.demo.order.domain.status;

import com.demo.order.BiTransitionTo;
import com.demo.order.model.Order;

import java.util.function.Supplier;

public final class Shipped implements OrderStatus, BiTransitionTo<Delivered, Refunded> {
    @Override
    public void onEnter(Order order) {
        order.log("Commande expédiée");
    }
    @Override
    public Delivered transitionToFirst(Supplier<Delivered> s) { return s.get(); }
    @Override
    public Refunded transitionToSecond(Supplier<Refunded> s) { return s.get(); }
}