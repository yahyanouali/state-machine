package com.demo.order.domain.status;

import com.demo.order.BiTransitionTo;
import com.demo.order.model.Order;

import java.util.function.Supplier;

public final class Pending implements OrderStatus, BiTransitionTo<CheckingOut, Cancelled> {
    @Override
    public void onEnter(Order order) {
        order.log("Commande en attente");
    }
    @Override
    public CheckingOut transitionToFirst(Supplier<CheckingOut> s) { return s.get(); }
    @Override
    public Cancelled transitionToSecond(Supplier<Cancelled> s) { return s.get(); }
}