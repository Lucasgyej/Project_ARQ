package com.banco.tp2_avance.service;

import com.banco.tp2_avance.model.Cuenta;
import com.banco.tp2_avance.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService {


    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public void depositar(String cbu, BigDecimal monto) {

        Cuenta cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().add(monto));
        cuentaRepository.save(cuenta);
    }

    @Override
    public void extraer(String cbu, BigDecimal monto) {

        Cuenta cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        if (cuenta.getSaldoOperativo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().subtract(monto));
        cuentaRepository.save(cuenta);
    }

    @Override
    public void transferir(String cbuOrigen, String cbuDestino, BigDecimal monto) {
        extraer(cbuOrigen, monto);
        depositar(cbuDestino, monto);
    }
}