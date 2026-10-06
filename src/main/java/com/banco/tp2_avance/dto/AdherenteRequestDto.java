package com.banco.tp2_avance.dto;

import com.banco.tp2_avance.enums.Parentesco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AdherenteRequestDto {

    @NotBlank(message = "El nombre o razón social no puede estar vacío")
    private String nombreRazonSocial;

    @NotBlank(message = "El CUIL es obligatorio")
    @Size(min = 11, max = 11, message = "El CUIL debe tener 11 dígitos")
    private String cuil;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotNull(message = "El parentesco es obligatorio (CONYUGE o HIJO)")
    private Parentesco parentesco;

    @NotNull(message = "El ID de la cuenta del titular es obligatorio")
    private Long cuentaTitularId;

    public AdherenteRequestDto() {}

    // Getters y Setters
    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public void setNombreRazonSocial(String nombreRazonSocial) { this.nombreRazonSocial = nombreRazonSocial; }

    public String getCuil() { return cuil; }
    public void setCuil(String cuil) { this.cuil = cuil; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Parentesco getParentesco() { return parentesco; }
    public void setParentesco(Parentesco parentesco) { this.parentesco = parentesco; }

    public Long getCuentaTitularId() { return cuentaTitularId; }
    public void setCuentaTitularId(Long cuentaTitularId) { this.cuentaTitularId = cuentaTitularId; }
}
