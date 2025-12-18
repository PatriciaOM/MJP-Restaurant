package com.mjprestaurant.model.orderTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.order.OrderGetResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para OrderGetResponse
 */
public class OrderGetResponseTests {

    @Test
    void testDefaultConstructor() {
        OrderGetResponse response = new OrderGetResponse();
        assertNull(response.getMessageStatus(), "El mensaje de estado debería ser null");
        assertNull(response.getItems(), "La lista de órdenes debería ser null");
    }

    @Test
    void testFullConstructor() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(1L, 1L, null, Order.Status.OPEN));
        OrderGetResponse response = new OrderGetResponse("OK", list);

        assertEquals("OK", response.getMessageStatus());
        assertEquals(list, response.getItems());
        assertEquals(1, response.getItems().size());
        assertEquals(1L, response.getItems().get(0).getIdSessionService());
    }

    @Test
    void testCopyConstructor() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(2L, 20L, null, Order.Status.SERVED));
        OrderGetResponse original = new OrderGetResponse("SUCCESS", list);

        OrderGetResponse copy = new OrderGetResponse(original);
        assertEquals("SUCCESS", copy.getMessageStatus());
        assertEquals(list, copy.getItems());
    }

    @Test
    void testSettersAndGetters() {
        OrderGetResponse response = new OrderGetResponse();
        List<Order> list = new ArrayList<>();
        list.add(new Order(3L, 30L, null, Order.Status.SENDED));

        response.setMessageStatus("UPDATED");
        response.setItems(list);

        assertEquals("UPDATED", response.getMessageStatus());
        assertEquals(list, response.getItems());
        assertEquals(1, response.getItems().size());
        assertEquals(30L, response.getItems().get(0).getIdSessionService());
    }
}
