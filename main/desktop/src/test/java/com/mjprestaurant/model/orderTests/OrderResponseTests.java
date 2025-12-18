package com.mjprestaurant.model.orderTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.order.OrderResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderResponseTests {

    @Test
    void testDefaultConstructor() {
        OrderResponse response = new OrderResponse();
        assertNull(response.getMessageStatus(), "El mensaje de estado debería ser null");
        assertNull(response.getItems(), "La lista de órdenes debería ser null");
    }

    @Test
    void testFullConstructor() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, 10L, null, Order.Status.OPEN));
        OrderResponse response = new OrderResponse("OK", orders);

        assertEquals("OK", response.getMessageStatus());
        assertEquals(orders, response.getItems());
        assertEquals(1, response.getItems().size());
        assertEquals(10L, response.getItems().get(0).getIdSessionService());
    }

    @Test
    void testCopyConstructor() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(2L, 20L, null, Order.Status.SERVED));
        OrderResponse original = new OrderResponse("SUCCESS", orders);

        OrderResponse copy = new OrderResponse(original);
        assertEquals("SUCCESS", copy.getMessageStatus());
        assertEquals(orders, copy.getItems());
    }

    @Test
    void testSettersAndGetters() {
        OrderResponse response = new OrderResponse();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(3L, 30L, null, Order.Status.SENDED));

        response.setMessageStatus("UPDATED");
        response.setItems(orders);

        assertEquals("UPDATED", response.getMessageStatus());
        assertEquals(orders, response.getItems());
        assertEquals(1, response.getItems().size());
        assertEquals(30L, response.getItems().get(0).getIdSessionService());

        // probar los alias de messageData
        response.setMessageData(orders);
        assertEquals(orders, response.getMessageData());
    }
}

