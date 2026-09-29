package com.banco.tp2_avance.dto;

import java.math.BigDecimal;

public class CuentaResponseDto {

    private Long id;
    private String cbu;
    private String alias;
    private BigDecimal saldoOperativo;
    private String estado;

    public CuentaResponseDto() {}

    public CuentaResponseDto(Long id, String cbu, String alias, BigDecimal saldoOperativo, String estado) {
        this.id = id;
        this.cbu = cbu;
        this.alias = alias;
        this.saldoOperativo = saldoOperativo;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCbu() { return cbu; }
    public void setCbu(String cbu) { this.cbu = cbu; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public BigDecimal getSaldoOperativo() { return saldoOperativo; }
    public void setSaldoOperativo(BigDecimal saldoOperativo) { this.saldoOperativo = saldoOperativo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}