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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Controlador per a la interfície de cambrers.
 * @author Patricia Oliva
 */
public class WaiterController {

    private WaiterFrame waiterFrame;
    private String token;

    /**
     * Constructor que inicialitza la finestra de cambrer amb el token de sessió.
     * @param token token de sessió
     * @param login finestra de login
     */
    public WaiterController(String token, LoginFrame login) {
        this.token = token;

        // Creamos la ventana de camarero y le pasamos la acción para obtener las mesas con estado
        waiterFrame = new WaiterFrame(login, token, "Finestra cambrers", this::fetchTablesWithStatus);
        waiterFrame.setLocationRelativeTo(null);
        waiterFrame.refresh();
        waiterFrame.setVisible(true);
    }

    /**
     * Combina totes les taules amb l'estat actual de cada una.
     * @return llista d'elements TableStatusResponseElement amb informació de cada taula
     */
    private List<TableStatusResponseElement> fetchTablesWithStatus() {
        List<TableStatusResponseElement> result = new ArrayList<>();
        try {
            List<TableRestaurant> allTables = TableController.getAllTables(token);
            TableStatusResponse status = getTableStatus(token);

            for (TableRestaurant t : allTables) {
                int clientsAmount = 0;
                if (status != null) {
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
     * Consulta l'endpoint /table/status i retorna l'estat de les taules.
     * @param token token de sessió
     * @return TableStatusResponse amb l'estat de les taules
     * @throws ControllerException si hi ha error de connexió o resposta
     */
    private TableStatusResponse getTableStatus(String token) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/table/status");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

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
                throw new ControllerException("Error en la resposta del servidor: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de connexió al servidor.");
        }
    }

    /**
     * Obté una sessió concreta per identificador.
     * @param sessionId identificador de la sessió
     * @return objecte SessionService o null si no existeix
     * @throws ControllerException si hi ha error en la connexió o resposta
     */
    public SessionService getSessionById(Long sessionId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/session/get");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = mapper.writeValueAsString(
                new SessionServiceGetInfo(token, sessionId)
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                SessionServiceGetResponse response =
                    mapper.readValue(conn.getInputStream(), SessionServiceGetResponse.class);

                conn.disconnect();

                if (response.getSessionServices() != null && !response.getSessionServices().isEmpty()) {
                    return response.getSessionServices().get(0);
                }
                return null;
            } else {
                conn.disconnect();
                throw new ControllerException("Error servidor: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error obtenint la sessió.");
        }
    }

    /**
     * Marca una sessió com pagada.
     * @param session sessió a actualitzar
     * @throws ControllerException si hi ha error en la connexió o resposta
     */
    public void markSessionAsPaid(SessionService session) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            URL url = new URL("https://localhost:8080/session/update");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

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

    /** @return la finestra del cambrer */
    public WaiterFrame getWaiterFrame() {
        return waiterFrame;
    }
}
