package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.Worker;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.WorkerFrame;

/**
 * Classe controller de la pantalla d'edició de treballadors
 * @author Patricia Oliva
 */
public class WorkerController implements ActionListener {

    private static final String[] workerFields = {
        "DNI",
        "Nom",
        "Cognoms",
        "Data d'inici",
        "Torn",
        "Rol"
        };
    private WorkerFrame workerFrame;
    private LoginFrame login;
    private int workerId = 0;

    /**
     * Constructor principal
     * @param workerFrame pantalla d'edició de treballadors
     * @param login pantalla de login inicial
     */
    public WorkerController(WorkerFrame workerFrame, LoginFrame login) {
        this.workerFrame = workerFrame;
        this.login = login;
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
            deleteWorker();
            System.out.println("Botón 'Eliminar' pulsado");
        }   
    }

    /**
     * Mètode per crear un nou treballador
     * Aquest mètode crea un nou formulari amb els camps passats per pàrametre (a la constant) i els valida un a un.
     * Quan comprova que tots son vàlids, els envia al server
     */
    public void createWorker(){

        CustomComponents.createForm("Nou treballador", workerFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> workerData = (Map<String, String>) e.getSource();
            
            // Validar que tots els camps estiguin omplerts
            for (String key : workerData.keySet()) {
                if (workerData.get(key).isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "El camp \"" + key + "\" no pot estar buit.",
                            "Error de validació", JOptionPane.ERROR_MESSAGE);
                    CustomComponents.clearField(key); // Neteja només aquell camp
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
            String dateStr = workerData.get("Data d'inici");
            if (!dateStr.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                JOptionPane.showMessageDialog(null,
                        "La data d'inici ha d'estar en format dd/mm/yyyy (ex: 05/11/2025).",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Data d'inici");
                return;
            }

            // Validar torn
            String shift = workerData.get("Torn");
            String normalizedShift = shift.toLowerCase()
                    .replace("í", "i")
                    .replace("à", "a")
                    .trim();

            if (!normalizedShift.matches("^(mati|tarda|fulltime)$")) {
                JOptionPane.showMessageDialog(null,
                        "El torn ha de ser 'matí', 'tarda' o 'fulltime'.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Torn");
                return;
            }

            // Validar rol
            String role = workerData.get("Rol");
            String normalizedRole = role.toLowerCase()
                    .trim();

            if (!normalizedRole.matches("^(user|admin)$")) {
                JOptionPane.showMessageDialog(null,
                        "El rol ha de ser 'user' o 'admin'.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("Rol");
                return;
            }

            System.out.println("Dades vàlides");
            workerId++; //VERIFICAR QUE NO HAYA QUE MODIFICAR ESTA PARTE PORQUE ESTÉ DUPLICADO
            workerData.put("id", String.valueOf(workerId));

            // Si todas las validaciones pasan, hacemos el POST al servidor
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(workerData);

                HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/worker").openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes());
                    os.flush();
                }

                if (conn.getResponseCode() == 200) {
                    JOptionPane.showMessageDialog(null, "Treballador creat correctament!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error del servidor: " + conn.getResponseCode(), "Error", JOptionPane.ERROR_MESSAGE);
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
    public void deleteWorker() {
        String[] deleteFields = {"ID del treballador"};

        CustomComponents.createForm("Eliminar treballador", deleteFields, e -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) e.getSource();

            String workerId = data.get("ID del treballador");

            // Validación
            if (workerId == null || workerId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Has d'introduir l'ID del treballador.",
                        "Error de validació", JOptionPane.ERROR_MESSAGE);
                CustomComponents.clearField("ID del treballador");
                return;
            }

            try {
                URL url = new URL("http://localhost:8080/workers/" + workerId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Content-Type", "application/json");

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    JOptionPane.showMessageDialog(null,
                            "Treballador eliminat correctament.",
                            "Èxit", JOptionPane.INFORMATION_MESSAGE);
                } else if (responseCode == 404) {
                    JOptionPane.showMessageDialog(null,
                            "No s'ha trobat cap treballador amb aquest ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    CustomComponents.clearField("ID del treballador");
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

        }, e -> System.out.println("Eliminació cancel·lada"));
    }

    /**
     * Mètode de consulta de treballadors
     * @return llista de tots els treballadors
     * @throws ControllerException
     */
    public List<Worker> getAllWorkers() throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL("http://localhost:8080/worker");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                List<Worker> workers = mapper.readValue(
                    conn.getInputStream(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Worker.class)
                );
                conn.disconnect();
                return workers;
            } else {
                throw new ControllerException("Error en la resposta del servidor: ");
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
                URL url = new URL("http://localhost:8080/workers/" + workerId);
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
