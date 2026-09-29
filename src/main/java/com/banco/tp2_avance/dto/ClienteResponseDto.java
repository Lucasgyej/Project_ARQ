package com.banco.tp2_avance.dto;

public class ClienteResponseDto {

    private Long id;
    private String nombreRazonSocial;
    private String cuil;
    private String email;

    public ClienteResponseDto() {
    }

    public ClienteResponseDto(Long id, String nombreRazonSocial, String cuil, String email) {
        this.id = id;
        this.nombreRazonSocial = nombreRazonSocial;
        this.cuil = cuil;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}