package com.mjprestaurant.model;

/**
 * Enumeració dels possibles torns de treball d'un usuari.
 * Coincideix amb els valors acceptats pel formulari de creació de treballadors.
 * 
 * @author Patricia Oliva
 */
public enum UserShift {

    MORNING("mati"),
    AFTERNOON("tarda"),
    FULL_TIME("indiferent");

    private final String value;

    private UserShift(String value) {
        this.value = value;
    }

    /**
     * Converteix una cadena (ex: "matí") en el corresponent valor de l'enum.
     * @param value valor en text (sense accents)
     * @return el valor corresponent de {@link UserShift}
     * @throws IllegalArgumentException si no coincideix amb cap torn vàlid
     */
    public static UserShift fromString(String value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("El valor del torn no pot ser nul.");
        }

        String normalized = value.toLowerCase()
                .replace("í", "i")
                .replace("à", "a")
                .trim();

        for (UserShift shift : UserShift.values()) {
            if (shift.value.equals(normalized)) {
                return shift;
            }
        }

        throw new IllegalArgumentException("Torn invàlid: " + value + ". Els valors vàlids són: matí, tarda, indiferent.");
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
