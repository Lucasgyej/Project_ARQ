package com.banco.tp2_avance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferenciaResponseDto {

    private String mensaje;
    private String cbuOrigen;
    private String cbuDestino;
    private BigDecimal monto;
    private LocalDateTime fechaOperacion;

    public TransferenciaResponseDto() {}

    public TransferenciaResponseDto(String mensaje, String cbuOrigen, String cbuDestino, BigDecimal monto, LocalDateTime fechaOperacion) {
        this.mensaje = mensaje;
        this.cbuOrigen = cbuOrigen;
        this.cbuDestino = cbuDestino;
        this.monto = monto;
        this.fechaOperacion = fechaOperacion;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getCbuOrigen() { return cbuOrigen; }
    public void setCbuOrigen(String cbuOrigen) { this.cbuOrigen = cbuOrigen; }

    public String getCbuDestino() { return cbuDestino; }
    public void setCbuDestino(String cbuDestino) { this.cbuDestino = cbuDestino; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDateTime getFechaOperacion() { return fechaOperacion; }
    public void setFechaOperacion(LocalDateTime fechaOperacion) { this.fechaOperacion = fechaOperacion; }
}