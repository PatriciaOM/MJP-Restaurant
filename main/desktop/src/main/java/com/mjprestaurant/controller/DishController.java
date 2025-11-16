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
import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.dish.DishCreateInfo;
import com.mjprestaurant.model.dish.DishDeleteInfo;
import com.mjprestaurant.model.dish.DishGetInfo;
import com.mjprestaurant.model.dish.DishGetResponse;
import com.mjprestaurant.model.dish.DishUpdateInfo;
import com.mjprestaurant.view.DishFrame;
import com.mjprestaurant.view.LoginFrame;

/**
 * Controller per la pantalla d'edició/gestió de plats
 * @author Patricia Oliva
 */
public class DishController implements ActionListener {
    private DishFrame dishFrame;
    private LoginFrame login;
    private String token;
    private CustomComponents editForm;

    private static final String[] dishCreateFields = {
        "Nom",
        "Preu",
        "Descripció",
        "Disponible (si|no)",
        "Categoria"
    };

    /**
     * Constructor principal
     * @param dishFrame pantalla de plats
     * @param login pantalla login
     * @param token token de sessió
     */
    public DishController(DishFrame dishFrame, LoginFrame login, String token) {
        this.dishFrame = dishFrame;
        this.login = login;
        this.token = token;
        addListeners();
    }

    /**
     * Afegir listeners als botons
     */
    private void addListeners() {
        dishFrame.getButtonAdd().addActionListener(this);
        dishFrame.getButtonDelete().addActionListener(this);
        dishFrame.initLogout(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == dishFrame.getButtonAdd()) {
            createDish();
            System.out.println("Botó 'Afegir' plats pulsat");
        } else if (source == dishFrame.getButtonDelete()) {
            deleteDish(token);
            System.out.println("Botó 'Eliminar' plats pulsat");
        }
    }

    /**
     * Crear plat: mostra formulari i envia al servidor
     */
    public void createDish() {
        CustomComponents customComponent = new CustomComponents();
        customComponent.createForm("Nou plat", dishCreateFields, evt -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) evt.getSource();

            String name = data.get("Nom");
            String priceStr = data.get("Preu");
            String desc = data.get("Descripció");
            String availableStr = data.get("Disponible (si|no)");
            String categoryStr = data.get("Categoria");

            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nom no pot estar buit.", "Error", JOptionPane.ERROR_MESSAGE);
                customComponent.clearField("Nom");
                return;
            }

            float price;
            try {
                price = Float.parseFloat(priceStr);
                if (price < 0) throw new NumberFormatException();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Preu invàlid. Introdueix un número positiu.", "Error", JOptionPane.ERROR_MESSAGE);
                customComponent.clearField("Preu");
                return;
            }

            boolean available = "si".equalsIgnoreCase(availableStr) || "sí".equalsIgnoreCase(availableStr) || "true".equalsIgnoreCase(availableStr);

            Dish.DishCategory category = Dish.DishCategory.OTHER;
            try {
                if (categoryStr != null && !categoryStr.trim().isEmpty()) {
                    category = Dish.DishCategory.valueOf(categoryStr.toUpperCase().trim());
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Categoria no vàlida.", "Error", JOptionPane.ERROR_MESSAGE);
                customComponent.clearField("Categoria");
                return;
            }

            Dish newEntry = new Dish(name, price, desc, available, category);
            newEntry.setId(null);

            DishCreateInfo createInfo = new DishCreateInfo(token, newEntry);

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                String json = mapper.writeValueAsString(createInfo);

                System.out.println(json);

                HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/dish/create").openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                int code = conn.getResponseCode();
                if (code == 200) {
                    JOptionPane.showMessageDialog(null, "Plat creat correctament", "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    customComponent.closeCurrentForm();
                    dishFrame.reloadDishesTable(token);
                } else {
                    JOptionPane.showMessageDialog(null, "Error del servidor: " + code, "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error de connexió amb el servidor", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }, evt -> System.out.println("Form cancel·lat"));
    }

    /**
     * Eliminar plat per id. Crea un formulari per demanar l'id del plat a eliminar
     * @param token token de sessió
     */
    public void deleteDish(String token) {
        String[] deleteFields = {"ID del plat"};

        CustomComponents customComponent = new CustomComponents();
        customComponent.createForm("Eliminar plat", deleteFields, evt -> {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) evt.getSource();

            String idStr = data.get("ID del plat");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Has d'introduir l'ID del plat.", "Error", JOptionPane.ERROR_MESSAGE);
                customComponent.clearField("ID del plat");
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID invàlid.", "Error", JOptionPane.ERROR_MESSAGE);
                customComponent.clearField("ID del plat");
                return;
            }

            DishDeleteInfo deleteInfo = new DishDeleteInfo(token, id);

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String json = mapper.writeValueAsString(deleteInfo);

                URL url = new URL("http://localhost:8080/dish/delete");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                int code = conn.getResponseCode();
                if (code == 200) {
                    JOptionPane.showMessageDialog(null, "Plat eliminat correctament.", "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    customComponent.closeCurrentForm();
                    dishFrame.reloadDishesTable(token);
                } else if (code == 404) {
                    JOptionPane.showMessageDialog(null, "No s’ha trobat cap plat coincident", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error del servidor: " + code, "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error de connexió amb el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }, evt -> System.out.println("Eliminació cancel·lada"));
    }

    /**
     * Consulta tots els plats
     * @param userToken token de sessió
     * @throws ControllerException
     * @return llista de plats
     */
    public static List<Dish> getAllDishes(String userToken) throws ControllerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            URL url = new URL("http://localhost:8080/dish/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonBody = mapper.writeValueAsString(new DishGetInfo(userToken));

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200) {
                DishGetResponse response = mapper.readValue(conn.getInputStream(), DishGetResponse.class);
                conn.disconnect();
                return response.getDishes();
            } else {
                System.out.println("HTTP Error: " + status);
                throw new com.mjprestaurant.model.ControllerException("Error en la resposta del servidor: " + status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new com.mjprestaurant.model.ControllerException("Error de connexió amb el servidor.");
        }
    }

    /**
     * Editar plat: busca el plat per id, demana camp i nou valor i envia al servidor
     * @param dishId id del plat a modificar
     */
    public void editDish(long dishId) {
        List<Dish> all;
        try {
            all = DishController.getAllDishes(token);

            Dish item = all.stream()
                    .filter(d -> d.getId() != null && d.getId() == dishId)
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                JOptionPane.showMessageDialog(null, "No s'ha trobat cap plat amb aquest ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] editFields = {"Camp a modificar", "Nou valor"};
            if (editForm != null && editForm.getCurrentFrame() != null) {
                editForm.closeCurrentForm();
            }

            editForm = new CustomComponents();
            editForm.createForm("Editar plat", editFields, evt -> {
                @SuppressWarnings("unchecked")
                Map<String, String> data = (Map<String, String>) evt.getSource();

                String field = data.get("Camp a modificar");
                String newValue = data.get("Nou valor");

                if (field == null || field.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Has d'introduir el camp a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                    editForm.clearField("Camp a modificar");
                    return;
                }
                if (newValue == null || newValue.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Has d'introduir el nou valor.", "Error", JOptionPane.ERROR_MESSAGE);
                    editForm.clearField("Nou valor");
                    return;
                }

                try {
                    switch (field.toLowerCase()) {
                        case "nom": item.setName(newValue); break;
                        case "preu":
                            item.setPrice(Float.parseFloat(newValue));
                            break;
                        case "descripció":
                        case "descripcio":
                        case "descripció ": item.setDescription(newValue); break;
                        case "disponible":
                            boolean av = "si".equalsIgnoreCase(newValue) || "sí".equalsIgnoreCase(newValue) || "true".equalsIgnoreCase(newValue);
                            item.setAvailable(av);
                            break;
                        case "categoria":
                            item.setCategory(Dish.DishCategory.valueOf(newValue.toUpperCase().trim()));
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "El camp introduït no és vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }

                    DishUpdateInfo updateInfo = new DishUpdateInfo(token, item);

                    try {
                        URL url = new URL("http://localhost:8080/dish/update");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setDoOutput(true);

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                        String json = mapper.writeValueAsString(updateInfo);
                        try (OutputStream os = conn.getOutputStream()) {
                            os.write(json.getBytes(StandardCharsets.UTF_8));
                            os.flush();
                        }

                        int responseCode = conn.getResponseCode();
                        if (responseCode == 200) {
                            JOptionPane.showMessageDialog(null, "Plat actualitzat correctament.", "Èxit", JOptionPane.INFORMATION_MESSAGE);
                            editForm.closeCurrentForm();
                            dishFrame.reloadDishesTable(token);
                        } else if (responseCode == 404) {
                            JOptionPane.showMessageDialog(null, "No s'ha trobat cap plat amb aquest ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error del servidor: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        conn.disconnect();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error de connexió amb el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de connexió amb el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                editForm.closeCurrentForm();
                editForm = null;
                dishFrame.reloadDishesTable(token);
            }, evt -> {
                System.out.println("Edició cancel·lada");
                editForm = null;
            });

        } catch (com.mjprestaurant.model.ControllerException e) {
            e.printStackTrace();
        }
    }
}
