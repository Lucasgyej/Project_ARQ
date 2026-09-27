package com.banco.tp2_avance.model;

import com.banco.tp2_avance.enums.EstadoCuenta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cajas_ahorro")
public class CajaDeAhorro extends Cuenta {

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteresAnual;

    @Column(nullable = false)
    private Integer extraccionesSinCosto;

    public CajaDeAhorro() {
    }

    public CajaDeAhorro(String cbu, String alias, BigDecimal saldoOperativo, EstadoCuenta estado,
                        BigDecimal tasaInteresAnual, Integer extraccionesSinCosto) {
        super(cbu, alias, saldoOperativo, estado);
        this.tasaInteresAnual = tasaInteresAnual;
        this.extraccionesSinCosto = extraccionesSinCosto;
    }

    public BigDecimal getTasaInteresAnual() { return tasaInteresAnual; }
    public void setTasaInteresAnual(BigDecimal tasaInteresAnual) { this.tasaInteresAnual = tasaInteresAnual; }

    public Integer getExtraccionesSinCosto() { return extraccionesSinCosto; }
    public void setExtraccionesSinCosto(Integer extraccionesSinCosto) { this.extraccionesSinCosto = extraccionesSinCosto; }
}
