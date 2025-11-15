package com.mjprestaurant.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class WorkerValidator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String validate(Map<String, String> data) {

        // Validar campos obligatorios
        for (String key : data.keySet()) {
            if (key.equals("Data de finalització (pot deixar-se en blanc)")) continue;
            if (data.get(key).isEmpty()) {
                return "El camp \"" + key + "\" no pot estar buit.";
            }
        }

        // Validar DNI
        String dni = data.get("DNI");
        if (!dni.matches("^[0-9]{8}[a-zA-Z]$")) {
            return "El camp DNI ha de tenir 8 números seguits d'una lletra.";
        }

        // Validar data d'inici
        String startDateStr = data.get("Data d'inici");
        if (!startDateStr.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            return "La data d'inici ha d'estar en format dd/mm/yyyy.";
        }

        LocalDate startDate = LocalDate.parse(startDateStr, FORMATTER);
        LocalDate today = LocalDate.now();
        if (startDate.isBefore(today)) {
            return "La data d'inici no pot ser anterior a la data actual.";
        }

        // Validar data de finalització (opcional)
        String endDateStr = data.get("Data de finalització (pot deixar-se en blanc)");
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {

            if (!endDateStr.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                return "La data de finalització ha d'estar en format dd/mm/yyyy.";
            }

            LocalDate endDate = LocalDate.parse(endDateStr, FORMATTER);

            if (endDate.isBefore(today)) {
                return "La data de finalització no pot ser anterior a la data actual.";
            }
            if (endDate.isBefore(startDate)) {
                return "La data de finalització no pot ser anterior a la data d'inici.";
            }
        }

        // Validar torn amb l'enum
        String shiftStr = data.get("Torn (matí | tarda | indiferent)").toLowerCase().trim();
        shiftStr = shiftStr.replace("í", "i");
        if (!shiftStr.equals("mati") && !shiftStr.equals("tarda") && !shiftStr.equals("indiferent")) {
            return "El torn ha de ser 'matí', 'tarda' o 'indiferent'.";
        }

        // Validar rol
        String role = data.get("Rol").toLowerCase().trim();
        if (!role.matches("^(user|admin)$")) {
            return "El rol ha de ser 'user' o 'admin'.";
        }

        return null; // Tot OK
    }

}
