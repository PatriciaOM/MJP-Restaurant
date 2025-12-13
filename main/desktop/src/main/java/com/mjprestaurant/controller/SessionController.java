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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SessionController {

    /**
     * Retorna la sessió de la taula consultada
     * @param token Token de sessió
     * @param tableId ID de la taula
     * @return SessionService si existeix, null si no hi ha sessió
     * @throws ControllerException Error llençat
     */
    public SessionService getSessionByTableId(String token, Long tableId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/session-service/get");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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

    /**
     * Retorna les sessions d'una taula
     * @param token token de sessió
     * @param tableId id de la taula
     * @return retorna la llista de sessions
     * @throws ControllerException error llençat
     */
    public List<SessionService> getSessionsByTableId(String token, Long tableId) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/session-service/get");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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
     * Actualitza una sessió al servidor
     * @param token Token de sessió
     * @param session SessionService a actualitzar
     * @throws ControllerException error llençat
     */
    public void updateSession(String token, SessionService session) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("https://localhost:8080/session-service/update");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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

