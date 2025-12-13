package com.mjprestaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.order.OrderGetInfo;
import com.mjprestaurant.model.order.OrderGetResponse;
import com.mjprestaurant.model.order.OrderUpdateInfo;

import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class OrderController {

    /**
     * Obté la comanda actual d'una taula fent servir la sessió i l'id de la taula
     * @param token Token de sessió
     * @param tableId ID de la taula
     * @return Comanda si existeix, null si no hi ha ordre
     * @throws ControllerException Error llençat
     */
    public Order getOrderByTableId(String token, Long tableId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/order/get");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OrderGetInfo request = new OrderGetInfo(token);
            request.setId(tableId); 
            String jsonBody = mapper.writeValueAsString(request);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                OrderGetResponse response = mapper.readValue(conn.getInputStream(), OrderGetResponse.class);
                conn.disconnect();
                List<Order> items = response.getItems();
                return (items != null && !items.isEmpty()) ? items.get(0) : null;
            } else if (conn.getResponseCode() == 404) {
                conn.disconnect();
                return null;
            } else {
                conn.disconnect();
                throw new ControllerException("Error a la resposta del servidor: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexió al servidor.");
        }
    }

    public List<Order> getOrdersByTableId(String token, Long tableId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/order/get");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            OrderGetInfo request = new OrderGetInfo(token);
            request.setId(tableId);

            String jsonBody = mapper.writeValueAsString(request);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                OrderGetResponse response = mapper.readValue(conn.getInputStream(), OrderGetResponse.class);
                conn.disconnect();

                List<Order> items = response.getItems();
                return (items != null) ? items : List.of();
            } else if (conn.getResponseCode() == 404) {
                conn.disconnect();
                return List.of();
            } else {
                conn.disconnect();
                throw new ControllerException("Error a la resposta del servidor: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexió al servidor.");
        }
    }


    /**
     * Actualitza una comanda al servidor
     * @param token Token de sessió
     * @param order Ordre a actualitzar
     * @throws ControllerException error llençat
     */
    public void updateOrder(String token, Order order) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


            URL url = new URL("https://localhost:8080/order/update");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OrderUpdateInfo request = new OrderUpdateInfo(token, order);
            String jsonBody = mapper.writeValueAsString(request);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != 200) {
                throw new ControllerException("Error actualizando la orden: " + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexión al servidor.");
        }
    }

}
