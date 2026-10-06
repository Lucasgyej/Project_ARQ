package com.banco.tp2_avance.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExtraccionRequestDto {

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long cuentaId;

    @NotNull(message = "El ID del cliente que opera es obligatorio")
    private Long clienteId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto a extraer debe ser mayor a 0")
    private BigDecimal monto;

    public ExtraccionRequestDto() {}

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}
