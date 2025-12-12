package com.mjprestaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceGetInfo;
import com.mjprestaurant.model.session.SessionServiceGetResponse;
import com.mjprestaurant.model.session.SessionServiceUpdateInfo;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SessionController {

    /**
     * Obtiene la sesión actual de una mesa usando el token y el id de la mesa
     * @param token Token de sesión
     * @param tableId ID de la mesa
     * @return SessionService si existe, null si no hay sesión
     * @throws ControllerException Error lanzado
     */
    public static SessionService getSessionByTableId(String token, Long tableId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/session-service/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            SessionServiceGetInfo request = new SessionServiceGetInfo(token, tableId);
            String jsonBody = mapper.writeValueAsString(request);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                SessionServiceGetResponse response = mapper.readValue(conn.getInputStream(), SessionServiceGetResponse.class);
                conn.disconnect();
                List<SessionService> items = response.getSessionServices();
                return (items != null && !items.isEmpty()) ? items.get(0) : null;
            } else if (conn.getResponseCode() == 404) {
                conn.disconnect();
                return null;
            } else {
                conn.disconnect();
                throw new ControllerException("Error en la respuesta del servidor: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexión al servidor.");
        }
    }

    public static List<SessionService> getSessionsByTableId(String token, Long tableId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/session-service/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            SessionServiceGetInfo request = new SessionServiceGetInfo(token);
            request.setId(tableId);

            String jsonBody = mapper.writeValueAsString(request);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                SessionServiceGetResponse response = mapper.readValue(conn.getInputStream(), SessionServiceGetResponse.class);
                conn.disconnect();

                List<SessionService> items = response.getSessionServices();
                return (items != null) ? items : List.of();
            } else if (conn.getResponseCode() == 404) {
                conn.disconnect();
                return List.of();
            } else {
                conn.disconnect();
                throw new ControllerException("Error en la respuesta del servidor: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexión al servidor.");
        }
    }

    /**
     * Actualiza una sesión en el servidor
     * @param token Token de sesión
     * @param session SessionService a actualizar
     * @throws ControllerException error lanzado
     */
    public static void updateSession(String token, SessionService session) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/session-service/update");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            SessionServiceUpdateInfo request = new SessionServiceUpdateInfo(token, session);
            String jsonBody = mapper.writeValueAsString(request);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != 200) {
                throw new ControllerException("Error actualizando la sesión: " + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error de conexión al servidor.");
        }
    }
}

