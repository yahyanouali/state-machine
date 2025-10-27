package com.demo.order.domain.status;

import com.demo.order.model.Order;

public final class Cancelled implements OrderStatus {
    @Override
    public void onEnter(Order order) {
        order.log("Commande annulée, remboursement si besoin");
    }
}
