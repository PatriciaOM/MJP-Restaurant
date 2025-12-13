package com.mjprestaurant.model.orderTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.order.OrderGetInfo;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para OrderGetInfo
 */
public class OrderGetInfoTests {

    @Test
    void testDefaultConstructor() {
        OrderGetInfo info = new OrderGetInfo();
        assertNull(info.getSessionToken());
        assertNull(info.getOrder());
        assertNull(info.getId());
        assertNull(info.getSearchType());
    }

    @Test
    void testConstructorAll() {
        OrderGetInfo info = new OrderGetInfo("token123");
        assertEquals("token123", info.getSessionToken());
        assertEquals(OrderGetInfo.SearchType.ALL, info.getSearchType());
    }

    @Test
    void testConstructorById() {
        OrderGetInfo info = new OrderGetInfo("tokenABC", 42L);
        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(42L, info.getId());
        assertEquals(OrderGetInfo.SearchType.BY_ID, info.getSearchType());
    }

    @Test
    void testFullConstructor() {
        Order order = new Order(1L, 10L, null, Order.Status.OPEN);
        OrderGetInfo info = new OrderGetInfo("token999", order, OrderGetInfo.SearchType.BY_ID, 5L);

        assertEquals("token999", info.getSessionToken());
        assertEquals(order, info.getOrder());
        assertEquals(OrderGetInfo.SearchType.BY_ID, info.getSearchType());
        assertEquals(5L, info.getId());
    }

    @Test
    void testCopyConstructor() {
        Order order = new Order(2L, 20L, null, Order.Status.SERVED);
        OrderGetInfo original = new OrderGetInfo("tokenCopy", order, OrderGetInfo.SearchType.ALL, 10L);
        OrderGetInfo copy = new OrderGetInfo(original);

        assertEquals("tokenCopy", copy.getSessionToken());
        assertEquals(order, copy.getOrder());
        assertEquals(OrderGetInfo.SearchType.ALL, copy.getSearchType());
        assertEquals(10L, copy.getId());
    }

    @Test
    void testSettersAndGetters() {
        Order order = new Order(3L, 30L, null, Order.Status.SENDED);
        OrderGetInfo info = new OrderGetInfo();
        info.setSessionToken("tokenAAA");
        info.setId(7L);
        info.setMessageData(7L); // debe reflejarse también en getId y getMessageData

        assertEquals("tokenAAA", info.getSessionToken());
        assertEquals(7L, info.getId());
        assertEquals(7L, info.getMessageData());
    }
}
