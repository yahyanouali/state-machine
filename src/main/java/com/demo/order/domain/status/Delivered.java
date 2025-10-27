package com.demo.order.domain.status;

import com.demo.order.model.Order;

public final class Delivered implements OrderStatus {
    @Override
    public void onEnter(Order order) {
        order.log("Livraison effectuée, demande avis client");
    }
}