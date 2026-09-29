package com.banco.tp2_avance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferenciaRequestDto {

    @NotBlank(message = "El CBU de origen es obligatorio")
    private String cbuOrigen;

    @NotBlank(message = "El CBU de destino es obligatorio")
    private String cbuDestino;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto de la transferencia debe ser mayor a cero")
    private BigDecimal monto;

    private String descripcion;

    public TransferenciaRequestDto() {}

    public String getCbuOrigen() { return cbuOrigen; }
    public void setCbuOrigen(String cbuOrigen) { this.cbuOrigen = cbuOrigen; }

    public String getCbuDestino() { return cbuDestino; }
    public void setCbuDestino(String cbuDestino) { this.cbuDestino = cbuDestino; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}