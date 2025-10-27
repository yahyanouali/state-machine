package com.demo.order.domain.status;

import com.demo.order.model.Order;

public final class Refunded implements OrderStatus {
    @Override
    public void onEnter(Order order) {
        order.log("Commande remboursée, avertir client");
    }
}