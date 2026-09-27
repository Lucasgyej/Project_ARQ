package com.banco.tp2_avance.model;

import com.banco.tp2_avance.enums.EstadoCuenta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas_corrientes")
public class CuentaCorriente extends Cuenta {

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal descubiertoAutorizado;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costoMantenimiento;

    public CuentaCorriente() {
    }

    public CuentaCorriente(String cbu, String alias, BigDecimal saldoOperativo, EstadoCuenta estado,
                           BigDecimal descubiertoAutorizado, BigDecimal costoMantenimiento) {
        super(cbu, alias, saldoOperativo, estado);
        this.descubiertoAutorizado = descubiertoAutorizado;
        this.costoMantenimiento = costoMantenimiento;
    }

    public BigDecimal getDescubiertoAutorizado() { return descubiertoAutorizado; }
    public void setDescubiertoAutorizado(BigDecimal descubiertoAutorizado) { this.descubiertoAutorizado = descubiertoAutorizado; }

    public BigDecimal getCostoMantenimiento() { return costoMantenimiento; }
    public void setCostoMantenimiento(BigDecimal costoMantenimiento) { this.costoMantenimiento = costoMantenimiento; }
}