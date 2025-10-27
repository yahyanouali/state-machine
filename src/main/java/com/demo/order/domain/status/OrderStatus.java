package com.demo.order.domain.status;

import com.demo.order.model.Order;

public sealed interface OrderStatus permits Pending, CheckingOut, Purchased,
        Shipped, Delivered, Cancelled, Failed, Refunded {

    void onEnter(Order order);

    default String getStatusName() {
        return getClass().getSimpleName();
    }

}
