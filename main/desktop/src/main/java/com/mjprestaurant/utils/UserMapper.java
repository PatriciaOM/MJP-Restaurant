package com.mjprestaurant.utils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.mjprestaurant.model.user.User;

public class UserMapper {
    /**
     * Mètode que mapeja un usuari per tal de facilitar la seva manipulació
     * @param user usuari a mapejar
     * @return mapa de camps : valors
     */
    public static Map<String, String> mapUserToWorkerData(User user) {
        Map<String, String> map = new HashMap<>();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        map.put("Login", user.getUsername());
        map.put("Contrasenya", user.getPassword());
        map.put("Rol", user.getRole());
        map.put("Nom", user.getName());
        map.put("Cognoms", user.getSurname());
        map.put("DNI", user.getDni());
        map.put("Torn (matí | tarda | indiferent)", user.getShift().toString().toLowerCase());
        map.put("Data d'inici", user.getStartDate().format(f));
        map.put(
            "Data de finalització (pot deixar-se en blanc)",
            user.getEndDate() != null ? user.getEndDate().format(f) : ""
        );

        return map;
    }
}
