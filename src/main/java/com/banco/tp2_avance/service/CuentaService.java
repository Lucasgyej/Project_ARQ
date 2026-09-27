package com.banco.tp2_avance.service;


import java.math.BigDecimal;

public interface CuentaService {

    void depositar(String cbu, BigDecimal monto);

    void extraer(String cbu, BigDecimal monto);

    void transferir(String cbuOrigen, String cbuDestino, BigDecimal monto);
}