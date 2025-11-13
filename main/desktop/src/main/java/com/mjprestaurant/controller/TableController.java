package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.table.TableCreateInfo;
import com.mjprestaurant.model.table.TableDeleteInfo;
import com.mjprestaurant.model.table.TableGetInfo;
import com.mjprestaurant.model.table.TableGetResponse;
import com.mjprestaurant.model.table.TableRestaurant;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.TableFrame;

/**
 * Classe controlador de la pantalla de gestió de taules del restaurant
 * @author Patricia Oliva
 */
public class TableController implements ActionListener {

    private static final String[] tableFields = {
        "Número de taula",
        "Màxim de comensals"
    };

    private TableFrame tableFrame;
    private LoginFrame login;
    private String token;

    /**
     * Constructor principal
     * @param tableFrame pantalla de gestió de taules
     * @param login pantalla de login inicial
     * @param token token de sessió
     */
    public TableController(TableFrame tableFrame, LoginFrame login, String token) {
        this.tableFrame = tableFrame;
        this.login = login;
        this.token = token;
        addListeners();
    }

    /**
     * Afegeix listeners als botons
     */
    private void addListeners() {
        tableFrame.getButtonAdd().addActionListener(this);
        tableFrame.getButtonDelete().addActionListener(this);
        tableFrame.initLogout(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == tableFrame.getButtonAdd()) {
            createTable();
        } else if (source == tableFrame.getButtonDelete()) {
            deleteTable(token);
        }
    }

    /**
     * Crea una nova taula
     */
    public void createTable() {
        CustomComponents.createForm("Nova taula", tableFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> tableData = (Map<String, String>) e.getSource();

            String numStr = tableData.get("Número de taula");
            String guestsStr = tableData.get("Màxim de comensals");

            // Validacions bàsiques
            if (numStr == null || numStr.trim().isEmpty() ||
                guestsStr == null || guestsStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Tots els camps són obligatoris.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int num, maxGuests;
            try {
                num = Integer.parseInt(numStr);
                maxGuests = Integer.parseInt(guestsStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Els valors han de ser numèrics.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (num <= 0 || maxGuests <= 0) {
                JOptionPane.showMessageDialog(null,
                        "El número de taula i el màxim de comensals han de ser positius.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear objecte TableRestaurant
            TableRestaurant table = new TableRestaurant(num, maxGuests);
            table.setId(null);

            // Crear objecte d’enviament
            TableCreateInfo tableCreate = new TableCreateInfo(token, table);

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String json = mapper.writeValueAsString(tableCreate);

                HttpURLConnection conn = (HttpURLConnection)
                        new URL("http://localhost:8080/table/create").openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                if (conn.getResponseCode() == 200) {
                    JOptionPane.showMessageDialog(null,
                            "Taula creada correctament",
                            "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    CustomComponents.closeCurrentForm();
                    tableFrame.reloadTables(token);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error del servidor: " + conn.getResponseCode(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.disconnect();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error de connexió amb el servidor.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }, e -> System.out.println("Creació cancel·lada"));
    }

    /**
     * Elimina una taula pel seu número
     */
    public void deleteTable(String token) {
        String[] deleteFields = {"Id de la taula"};

        CustomComponents.createForm("Eliminar taula", deleteFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) e.getSource();

            String numStr = data.get("Id de la taula");

            if (numStr == null || numStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Has d’introduir el número de taula.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long num;
            try {
                num = Long.parseLong(numStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "El número de taula ha de ser numèric.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TableDeleteInfo tableDelete = new TableDeleteInfo(token, num);

            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(tableDelete);

                URL url = new URL("http://localhost:8080/table/delete");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                if (conn.getResponseCode() == 200) {
                    JOptionPane.showMessageDialog(null,
                            "Taula eliminada correctament.",
                            "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    CustomComponents.closeCurrentForm();
                    tableFrame.reloadTables(token);
                } else if (conn.getResponseCode() == 404) {
                    JOptionPane.showMessageDialog(null,
                            "No s’ha trobat cap taula amb aquest número.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error del servidor: " + conn.getResponseCode(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.disconnect();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error de connexió amb el servidor.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }, e -> System.out.println("Eliminació cancel·lada"));
    }

    /**
     * Obté totes les taules del servidor
     */
    public static List<TableRestaurant> getAllTables(String userToken) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/table/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonBody = mapper.writeValueAsString(new TableGetInfo(userToken));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int status = conn.getResponseCode();

            if (status == 200) {
                TableGetResponse response = mapper.readValue(conn.getInputStream(), TableGetResponse.class);
                conn.disconnect();
                return response.getTables();
            } else {
                throw new ControllerException("Error en la resposta del servidor: " + status);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ControllerException("Error de connexió amb el servidor.");
        }
    }

    /**
     * Edita una taula (exemple genèric)
     */
    public static void editTable(int tableId) {
        String[] editFields = {"Camp a modificar", "Nou valor"};

        CustomComponents.createForm("Editar taula", editFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) e.getSource();

            String field = data.get("Camp a modificar");
            String newValue = data.get("Nou valor");

            if (field == null || field.trim().isEmpty() ||
                newValue == null || newValue.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Tots els camps són obligatoris.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                URL url = new URL("http://localhost:8080/table/" + tableId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String json = "{\"" + field + "\": \"" + newValue + "\"}";

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    JOptionPane.showMessageDialog(null,
                            "Taula actualitzada correctament.",
                            "Èxit", JOptionPane.INFORMATION_MESSAGE);
                } else if (responseCode == 404) {
                    JOptionPane.showMessageDialog(null,
                            "No s’ha trobat cap taula amb aquest ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error del servidor: " + responseCode,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.disconnect();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error de connexió amb el servidor.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }, e -> System.out.println("Edició cancel·lada"));
    }

}
