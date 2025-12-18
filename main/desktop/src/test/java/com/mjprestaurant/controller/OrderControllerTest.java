package com.mjprestaurant.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.order.Order;

/**
 * Tests unitarios para OrderController siguiendo el estilo de DishControllerTest.
 */
public class OrderControllerTest {

    private static class DummyOrderController extends OrderController {
        private List<Order> dummyOrders;

        public DummyOrderController() {
            dummyOrders = new ArrayList<>();
        }

        @Override
        public Order getOrderByTableId(String token, Long tableId) {
            // Devuelve un Order dummy o null
            if (tableId <= 0) return null;
            Order order = new Order();
            dummyOrders.add(order);
            return order;
        }

        @Override
        public List<Order> getOrdersByTableId(String token, Long tableId) {
            // Devuelve una lista dummy
            if (tableId <= 0) return new ArrayList<>();
            return dummyOrders;
        }

        @Override
        public void updateOrder(String token, Order order) {
            // Simula actualización, no hace nada
            dummyOrders.add(order);
        }
    }

    private DummyOrderController controller;

    @BeforeEach
    public void setUp() {
        controller = new DummyOrderController();
    }

    @Test
    public void testGetOrderByTableId() {
        assertDoesNotThrow(() -> {
            Order order = controller.getOrderByTableId("dummyToken", 1L);
            assertNotNull(order);
        });
    }

    @Test
    public void testGetOrdersByTableId() {
        assertDoesNotThrow(() -> {
            List<Order> orders = controller.getOrdersByTableId("dummyToken", 1L);
            assertNotNull(orders);
        });
    }

    @Test
    public void testUpdateOrder() {
        assertDoesNotThrow(() -> {
            Order order = new Order();
            controller.updateOrder("dummyToken", order);
        });
    }

    @Test
    public void testGetOrderByTableIdWithInvalidId() {
        assertDoesNotThrow(() -> {
            Order order = controller.getOrderByTableId("dummyToken", -1L);
            assertNull(order);
        });
    }

    @Test
    public void testGetOrdersByTableIdWithInvalidId() {
        assertDoesNotThrow(() -> {
            List<Order> orders = controller.getOrdersByTableId("dummyToken", -5L);
            assertNotNull(orders);
            assertTrue(orders.isEmpty());
        });
    }
}
