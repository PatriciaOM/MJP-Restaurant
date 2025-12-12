package com.mjprestaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceGetInfo;
import com.mjprestaurant.model.session.SessionServiceGetResponse;
import com.mjprestaurant.model.table.*;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.WaiterFrame;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WaiterController {

    private WaiterFrame waiterFrame;
    private String token;

    public WaiterController(String token, LoginFrame login) {
        this.token = token;

        // Creamos la ventana de camarero y le pasamos la acción para obtener las mesas con estado
        waiterFrame = new WaiterFrame(login, token, "Finestra cambrers", this::fetchTablesWithStatus);
        waiterFrame.setLocationRelativeTo(null);
        waiterFrame.refresh();
        waiterFrame.setVisible(true);

    }

    /**
     * Combina todas las mesas con el estado actual de cada mesa
     */
    private List<TableStatusResponseElement> fetchTablesWithStatus() {
        List<TableStatusResponseElement> result = new ArrayList<>();
        try {
            // 1. Trae todas las mesas
            List<TableRestaurant> allTables = TableController.getAllTables(token);

            // 2. Trae estado actual de las mesas
            TableStatusResponse status = getTableStatus(token);

            for (TableRestaurant t : allTables) {
                int clientsAmount = 0;
                if (status != null) {
                    // Busca si hay estado para esta mesa
                    for (TableStatusResponseElement e : status.getTables()) {
                        if (e.getId().equals(t.getId())) {
                            clientsAmount = e.getClientsAmount();
                            break;
                        }
                    }
                }
                result.add(new TableStatusResponseElement(t.getId(), t.getMaxGuests(), clientsAmount));
            }

        } catch (ControllerException e) {
            e.printStackTrace();
            // En caso de error, mostramos todas las mesas con 0/max
            try {
                List<TableRestaurant> allTables = TableController.getAllTables(token);
                for (TableRestaurant t : allTables) {
                    result.add(new TableStatusResponseElement(t.getId(), t.getMaxGuests(), 0));
                }
            } catch (ControllerException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Llama al endpoint /table/status y devuelve TableStatusResponse
     */
    private TableStatusResponse getTableStatus(String token) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/table/status");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Enviamos token como cuerpo JSON
            String jsonBody = mapper.writeValueAsString(new TableStatusInfo(token));
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                TableStatusResponse response = mapper.readValue(conn.getInputStream(), TableStatusResponse.class);
                conn.disconnect();
                return response;
            } else {
                conn.disconnect();
                throw new ControllerException("Error en la respuesta del servidor: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexión al servidor.");
        }
    }

    public SessionService getSessionById(Long sessionId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/session/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = mapper.writeValueAsString(
                new SessionServiceGetInfo(
                    token,
                    sessionId
                )
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                SessionServiceGetResponse response =
                    mapper.readValue(conn.getInputStream(), SessionServiceGetResponse.class);

                conn.disconnect();

                if (response.getSessionServices() != null &&
                    !response.getSessionServices().isEmpty()) {

                    return response.getSessionServices().get(0);
                }

                return null;
            } else {
                conn.disconnect();
                throw new ControllerException(
                    "Error servidor: " + conn.getResponseCode()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error obtenint la sessió.");
        }
    }

    public void markSessionAsPaid(SessionService session) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            URL url = new URL("http://localhost:8080/session/update");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            session.setStatus(SessionService.SessionServiceStatus.PAID);

            String json = mapper.writeValueAsString(session);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != 200) {
                conn.disconnect();
                throw new ControllerException("No s'ha pogut actualitzar la sessió.");
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error actualitzant la sessió.");
        }
    }

    public WaiterFrame getWaiterFrame() {
        return waiterFrame;
    }
}
