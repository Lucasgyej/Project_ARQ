package com.banco.tp2_avance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CuentaRequestDto {

    @NotBlank(message = "El CBU es obligatorio")
    @Size(min = 22, max = 22, message = "El CBU debe contener exactamente 22 caracteres")
    private String cbu;

    @NotBlank(message = "El alias es obligatorio")
    private String alias;

    @NotNull(message = "El saldo inicial no puede ser nulo")
    @PositiveOrZero(message = "El saldo inicial debe ser cero o positivo")
    private BigDecimal saldoInicial;

    @NotNull(message = "El ID del cliente titular es obligatorio")
    private Long clienteId;

    public CuentaRequestDto() {}

    public String getCbu() { return cbu; }
    public void setCbu(String cbu) { this.cbu = cbu; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}