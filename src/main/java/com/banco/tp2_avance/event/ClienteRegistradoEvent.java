package com.banco.tp2_avance.event;

public class ClienteRegistradoEvent {

    private final String email;
    private final String nombreRazonSocial;
    private final String token;

    public ClienteRegistradoEvent(String email, String nombreRazonSocial, String token) {
        this.email = email;
        this.nombreRazonSocial = nombreRazonSocial;
        this.token = token;
    }

    public String getEmail() { return email; }
    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public String getToken() { return token; }
}