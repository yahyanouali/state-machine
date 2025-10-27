package com.demo.order.rest;

import com.demo.order.domain.status.*;
import com.demo.order.model.Order;
import com.demo.order.repository.OrderRepository;
import com.demo.order.rest.dto.ApiError;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderRepository repository;

    public OrderResource(OrderRepository repository) {
        this.repository = repository;
    }

    @POST
    public Order createOrder(@QueryParam("customer") String customer,
                             @QueryParam("amount") double amount) {
        return repository.createOrder(customer, amount);
    }

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") String id) {
        return repository.findById(id)
                .map(o -> Response.ok(o).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError("Order not found",
                                "No order for id " + id)).build());
    }

    @POST
    @Path("/{id}/checkout")
    public Response payOrder(@PathParam("id") String id) {
        return repository.findById(id).map(order -> {
            if (order.status instanceof Pending p) {
                CheckingOut checkingOut = p.transitionToFirst(CheckingOut::new);
                order.transitionTo(checkingOut);
                repository.save(order);
                return Response.ok(order).build();
            }
            return badTransition(order, "checkout");
        }).orElse(notFound(id));
    }

    @POST
    @Path("/{id}/cancel")
    public Response cancelOrder(@PathParam("id") String id) {
        return repository.findById(id).map(order -> {
            if (order.status instanceof Pending p) {
                Cancelled cancelled = p.transitionToSecond(Cancelled::new);
                order.transitionTo(cancelled);
                repository.save(order);
                return Response.ok(order).build();
            } else if (order.status instanceof CheckingOut c) {
                Cancelled cancelled = c.transitionToSecond(Cancelled::new);
                order.transitionTo(cancelled);
                repository.save(order);
                return Response.ok(order).build();
            }
            return badTransition(order, "cancel");
        }).orElse(notFound(id));
    }

    @POST
    @Path("/{id}/pay")
    public Response paidOrder(@PathParam("id") String id) {
        return repository.findById(id).map(order -> {
            if (order.status instanceof CheckingOut c) {
                Purchased purchased = c.transitionToFirst(Purchased::new);
                order.transitionTo(purchased);
                repository.save(order);
                return Response.ok(order).build();
            }
            return badTransition(order, "pay");
        }).orElse(notFound(id));
    }

    @POST
    @Path("/{id}/ship")
    public Response shipOrder(@PathParam("id") String id) {
        return repository.findById(id).map(order -> {
            if (order.status instanceof Purchased p) {
                Shipped shipped = p.transitionToFirst(Shipped::new);
                order.transitionTo(shipped);
                repository.save(order);
                return Response.ok(order).build();
            }
            return badTransition(order, "ship");
        }).orElse(notFound(id));
    }

    @POST
    @Path("/{id}/deliver")
    public Response deliverOrder(@PathParam("id") String id) {
        return repository.findById(id).map(order -> {
            if (order.status instanceof Shipped s) {
                Delivered delivered = s.transitionToFirst(Delivered::new);
                order.transitionTo(delivered);
                repository.save(order);
                return Response.ok(order).build();
            }
            return badTransition(order, "deliver");
        }).orElse(notFound(id));
    }

    @GET
    @Path("/{id}/history")
    public Response getHistory(@PathParam("id") String id) {
        return repository.findById(id)
                .map(order -> Response.ok(order.history).build())
                .orElse(notFound(id));
    }

    /* utilitaires internes */
    private static Response badTransition(Order order, String action) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ApiError("Transition error",
                        "Transition " + action + " impossible depuis " + order.status.getStatusName())).build();
    }

    private static Response notFound(String id) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ApiError("Order not found", "No order for id " + id)).build();
    }
}
