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
import com.mjprestaurant.model.user.User;
import com.mjprestaurant.model.user.UserCreateInfo;
import com.mjprestaurant.model.user.UserCreateResponse;
import com.mjprestaurant.model.user.UserDeleteInfo;
import com.mjprestaurant.model.user.UserGetInfo;
import com.mjprestaurant.model.user.UserGetResponse;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.WorkerFrame;

/**
 * Classe controller de la pantalla d'edició de treballadors
 * @author Patricia Oliva
 */
public class WorkerController implements ActionListener {

    /*private static final String[] workerFields = {
        "DNI",
        "Nom",
        "Cognoms",
        "Data d'inici",
        "Torn",
        "Rol"
        };*/
    private static final String[] workerFields = {
        "Login",
        "Contrasenya",
        "Rol",
        "Nom",
        "Cognoms",
        "DNI",
        "Torn (matí | tarda | indiferent)",
        "Data d'inici",
        "Data de finalització (pot deixar-se en blanc)"
    };
    private WorkerFrame workerFrame;
    private LoginFrame login;
    private String token;

    /**
     * Constructor principal
     * @param workerFrame pantalla d'edició de treballadors
     * @param login pantalla de login inicial
     */
    public WorkerController(WorkerFrame workerFrame, LoginFrame login, String token) {
        this.workerFrame = workerFrame;
        this.login = login;
        this.token = token;
        System.out.println("Token" + this.token);
        addListeners();
    }

    /**
     * Mètode per afegir els listeners
     */
    private void addListeners() {
        workerFrame.getButtonAdd().addActionListener(this);
        workerFrame.getButtonDelete().addActionListener(this);
        workerFrame.initLogout(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == workerFrame.getButtonAdd()){
                createWorker();
            System.out.println("Botón 'Afegir' pulsado");
        }
        else if (source == workerFrame.getButtonDelete()){
            deleteWorker(token);
            System.out.println("Botón 'Eliminar' pulsado");
        }   
    }

    /**
     * Mètode per crear un nou treballador
     * Aquest mètode crea un nou formulari amb els camps passats per pàrametre (a la constant) i els valida un a un.
     * Quan comprova que tots son vàlids, els envia al server
     */
    public void createWorker() {

        CustomComponents.createForm("Nou treballador", workerFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> workerData = (Map<String, String>) e.getSource();

            // Validar que tots els camps estiguin omplerts (excepte Data de finalització)
            for (String key : workerData.keySet()) {
                if (key.equals("Data de finalització (pot deixar-se en blanc)")) continue;
                if (workerData.get(key).isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "El camp \"" + key + "\" no pot estar buit.",
                            "Error de validació", JOptionPane.ERROR_MESSAGE);
                    CustomComponents.clearField(key);
                    return;
                }
            }

            // Validar DNI → 8 números + 1 lletra
            String dni = workerData.get("DNI");
            if (!dni.matches("^[0-9]{8}[a-zA-Z]$")) {
                JOptionPane.showMessageDialog(null,
                        "El camp DNI ha de tenir 8 números seguits d'una lletra (ex: 12345678A).",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("DNI");
                return;
            }

            // Validar data d'inici → format dd/mm/yyyy
            String startDateStr = workerData.get("Data d'inici");
            if (!startDateStr.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                JOptionPane.showMessageDialog(null,
                        "La data d'inici ha d'estar en format dd/mm/yyyy (ex: 05/11/2025).",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Data d'inici");
                return;
            }

            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate startDate = java.time.LocalDate.parse(startDateStr, formatter);
            java.time.LocalDate today = java.time.LocalDate.now();

            if (startDate.isBefore(today)) {
                JOptionPane.showMessageDialog(null,
                        "La data d'inici no pot ser anterior a la data actual.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Data d'inici");
                return;
            }

            // Validar data de finalització (pot estar buida)
            String endDateStr = workerData.get("Data de finalització (pot deixar-se en blanc)");
            java.time.LocalDate endDate = null;

            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                if (!endDateStr.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                    JOptionPane.showMessageDialog(null,
                            "La data de finalització ha d'estar en format dd/mm/yyyy (ex: 10/11/2025).",
                            "Error de validació", JOptionPane.ERROR_MESSAGE);
                    CustomComponents.clearField("Data de finalització (pot deixar-se en blanc)");
                    return;
                }

                endDate = java.time.LocalDate.parse(endDateStr, formatter);

                if (endDate.isBefore(today)) {
                    JOptionPane.showMessageDialog(null,
                            "La data de finalització no pot ser anterior a la data actual.",
                            "Error de validació", JOptionPane.ERROR_MESSAGE);
                    CustomComponents.clearField("Data de finalització (pot deixar-se en blanc)");
                    return;
                }

                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(null,
                            "La data de finalització no pot ser anterior a la data d'inici.",
                            "Error de validació", JOptionPane.ERROR_MESSAGE);
                    CustomComponents.clearField("Data de finalització (pot deixar-se en blanc)");
                    return;
                }
            }

            // Validar torn amb l'enum UserShift
            String shiftStr = workerData.get("Torn (matí | tarda | indiferent)");
            com.mjprestaurant.model.user.UserShift userShift;
            try {
                userShift = com.mjprestaurant.model.user.UserShift.fromString(shiftStr);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null,
                        "El torn ha de ser 'matí', 'tarda' o 'indiferent'.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Torn (matí | tarda | indiferent)");
                return;
            }

            // Validar rol
            String role = workerData.get("Rol");
            String normalizedRole = role.toLowerCase().trim();
            if (!normalizedRole.matches("^(user|admin)$")) {
                JOptionPane.showMessageDialog(null,
                        "El rol ha de ser 'user' o 'admin'.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Rol");
                return;
            }

            // Crear usuari amb tots els camps del formulari
            User user = new User(
                    workerData.get("Login"),
                    workerData.get("Contrasenya"),
                    normalizedRole.toUpperCase(),
                    workerData.get("Nom"),
                    workerData.get("Cognoms"),
                    userShift,
                    startDate,
                    endDate,
                    dni
            );
            user.setId(null);

            // Crear objecte per enviar al servidor
            UserCreateInfo userCreate = new UserCreateInfo(
                    token,
                    user
            );

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String json = mapper.writeValueAsString(userCreate);

                HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/user/create").openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes());
                    os.flush();
                }

                if (conn.getResponseCode() == 200) {
                    JOptionPane.showMessageDialog(null,
                            "Treballador creat correctament",
                            "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    CustomComponents.closeCurrentForm();
                    workerFrame.reloadUsersTable(token);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error del servidor: " + conn.getResponseCode(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.disconnect();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error de connexió amb el servidor", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }, e -> System.out.println("Form cancel·lat"));
    }


    /**
     * Mètode que ens serveix per eliminar a un treballador
     * Crea un nou formulari a on es demana l'id del treballador i ho envia al server
     */
    public void deleteWorker(String token) {
        String[] deleteFields = {"Login del treballador"};

        CustomComponents.createForm("Eliminar treballador", deleteFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) e.getSource();

            String workerName = data.get("Login del treballador");

            // Validación
            if (workerName == null || workerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Has d'introduir el login del treballador.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Login del treballador");
                return;
            }

            UserDeleteInfo userDelete = new UserDeleteInfo(token, workerName);

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String json = mapper.writeValueAsString(userDelete);

                URL url = new URL("http://localhost:8080/user/delete");
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
                            "Treballador eliminat correctament.",
                            "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    CustomComponents.closeCurrentForm();
                    workerFrame.reloadUsersTable(token);
                } else if (conn.getResponseCode() == 404) {
                    JOptionPane.showMessageDialog(null,
                            "No s’ha trobat cap treballador coincident",
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
     * Mètode de consulta de treballadors
     * @return llista de tots els treballadors
     * @throws ControllerException
     */
    public static List<User> getAllWorkers(String userToken) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/user/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // Permitir enviar un cuerpo JSON

            // Crear el cuerpo de la petición con el token
            String jsonBody = mapper.writeValueAsString(new UserGetInfo(userToken));

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();

            if (status == 200) {
                // Leer la respuesta JSON
                UserGetResponse response = mapper.readValue(conn.getInputStream(), UserGetResponse.class);
                conn.disconnect();
                return response.getUser();
            } else {
                System.out.println("HTTP Error: " + status);
                throw new ControllerException("Error en la resposta del servidor: " + status);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ControllerException("Error de connexió amb el servidor.");
        }
    }

    /**
     * Mètode d'edició de treballadors
     * Es crea un formulari demanant el camp que es vol modificar i el nou valor que ha de tenir i s'envia al server
     * @param workerId id del treballador seleccionat
     */
    public static void editWorker(int workerId) {
        String[] editFields = {"Camp a modificar", "Nou valor"};

        CustomComponents.createForm("Editar treballador", editFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) e.getSource();

            String field = data.get("Camp a modificar");
            String newValue = data.get("Nou valor");

            if (field == null || field.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Has d'introduir el camp a modificar.",
                    "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Camp a modificar");
                return;
            }

            if (newValue == null || newValue.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Has d'introduir el nou valor.",
                    "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Nou valor");
                return;
            }

            try {
                URL url = new URL("http://localhost:8080/user/update" + workerId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT"); // PUT para actualizar
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Crear JSON con campo y valor
                String json = "{\"" + field + "\": \"" + newValue + "\"}";

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes());
                    os.flush();
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    JOptionPane.showMessageDialog(null, 
                        "Treballador actualitzat correctament.",
                        "Èxit", JOptionPane.INFORMATION_MESSAGE);
                } else if (responseCode == 404) {
                    JOptionPane.showMessageDialog(null, 
                        "No s'ha trobat cap treballador amb aquest ID.",
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
