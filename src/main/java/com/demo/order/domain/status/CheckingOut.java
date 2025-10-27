package com.demo.order.domain.status;

import com.demo.order.BiTransitionTo;
import com.demo.order.model.Order;

import java.util.function.Supplier;

public final class CheckingOut implements OrderStatus, BiTransitionTo<Purchased, Cancelled> {

    @Override
    public void onEnter(Order order) {
        order.log("Paiement en cours");
    }
    @Override
    public Purchased transitionToFirst(Supplier<Purchased> s) { return s.get(); }

    @Override
    public Cancelled transitionToSecond(Supplier<Cancelled> s) { return s.get(); }
}