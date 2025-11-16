package com.mjprestaurant.model.user;

/**
 * Classe que conté la informació necessària per obtenir usuaris del servidor.
 * @author Patricia Oliva
 */
public class UserGetInfo {

    public enum SearchType {
        ALL,
        BY_ID,
        BY_USERNAME
    }

    private long id = -1;
    public String username = null; 
    private String sessionToken = null;
    private SearchType searchType = SearchType.ALL;

    /**
     * Constructor per defecte.
     */
    public UserGetInfo() {}

    /**
     * Constructor per obtenir tots els usuaris. Només requereix el token de sessió.
     * @param sessionToken token de sessió
     */
    public UserGetInfo(String sessionToken) {
        this.searchType = SearchType.ALL;
        this.sessionToken = sessionToken;
    }

    /**
     * Constructor per obtenir un usuari per Id.
     * @param sessionToken token de sessió
     * @param id Id de l'usuari
     */
    public UserGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }

    /**
     * Constructor per obtenir un usuari per nom d'usuari.
     * @param sessionToken token de sessió
     * @param username nom d'usuari
     */
    public UserGetInfo(String sessionToken, String username) {
        this.sessionToken = sessionToken;
        this.username = username;
        this.searchType = SearchType.BY_USERNAME;
    }

    /**
     * Inicialitza el nom d'usuari.
     * @param val nom d'usuari
     */
    public void setUserName(String val) {
        this.username = val;
    }

    /**
     * Retorna el nom d'usuari.
     * @return nom d'usuari
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Inicialitza el token de sessió.
     * @param val token de sessió
     */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }

    /**
     * Retorna el token de sessió.
     * @return token de sessió
     */
    public String getSessionToken() {
        return this.sessionToken;
    }

    /**
     * Retorna l'Id de l'usuari.
     * @return Id de l'usuari
     */
    public long getId() {
        return id;
    }

    /**
     * Inicialitza l'Id de l'usuari.
     * @param id Id de l'usuari
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Inicialitza el tipus de cerca.
     * @param searchType tipus de cerca
     */
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    /**
     * Retorna el tipus de cerca.
     * @return tipus de cerca
     */
    public SearchType getSearchType() {
        return searchType;
    }

}
