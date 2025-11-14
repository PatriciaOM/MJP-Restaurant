package com.mjprestaurant.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.mjprestaurant.model.user.User;

public class WorkerFactory {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static User fromMap(Map<String, String> data) {

        LocalDate startDate = LocalDate.parse(
                data.get("Data d'inici"), FORMATTER);

        String end = data.get("Data de finalització (pot deixar-se en blanc)");
        LocalDate endDate = (end == null || end.isBlank()) 
                ? null 
                : LocalDate.parse(end, FORMATTER);

        return new User(
                data.get("Login"),
                data.get("Contrasenya"),
                data.get("Rol").toUpperCase().trim(),
                data.get("Nom"),
                data.get("Cognoms"),
                com.mjprestaurant.model.user.UserShift.fromString(
                        data.get("Torn (matí | tarda | indiferent)")
                ),
                startDate,
                endDate,
                data.get("DNI")
        );
    }

}
