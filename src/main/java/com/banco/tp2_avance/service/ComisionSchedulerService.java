package com.banco.tp2_avance.service;

import com.banco.tp2_avance.enums.EstadoCuenta;
import com.banco.tp2_avance.enums.EstadoTransaccion;
import com.banco.tp2_avance.enums.TipoTransaccion;
import com.banco.tp2_avance.model.CajaDeAhorro;
import com.banco.tp2_avance.model.Cuenta;
import com.banco.tp2_avance.model.CuentaCorriente;
import com.banco.tp2_avance.model.Transaccion;
import com.banco.tp2_avance.repository.CuentaRepository;
import com.banco.tp2_avance.repository.TransaccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComisionSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(ComisionSchedulerService.class);

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    @Value("${app.comisiones.cuenta-corriente:5000.00}")
    private BigDecimal comisionCuentaCorriente;

    @Value("${app.comisiones.caja-ahorro:2000.00}")
    private BigDecimal comisionCajaAhorro;

    public ComisionSchedulerService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Scheduled(cron = "${app.comisiones.cron:0 0 0 1 * *}")
    @Transactional
    public void ejecutarLiquidacionMensual() {
        log.info("========== INICIANDO PROCESO DE LIQUIDACIÓN DE COMISIONES ==========");

        List<Cuenta> cuentasActivas = cuentaRepository.findByEstado(EstadoCuenta.ACTIVA);

        if (cuentasActivas.isEmpty()) {
            log.info("No se encontraron cuentas activas para liquidar.");
            return;
        }

        int procesadas = 0;

        for (Cuenta cuenta : cuentasActivas) {
            BigDecimal comisionADebitar = BigDecimal.ZERO;

            if (cuenta instanceof CuentaCorriente) {
                comisionADebitar = comisionCuentaCorriente;
            } else if (cuenta instanceof CajaDeAhorro) {
                comisionADebitar = comisionCajaAhorro;
            }

            if (comisionADebitar.compareTo(BigDecimal.ZERO) > 0) {
                // 1. Debitar del saldo operativo
                cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().subtract(comisionADebitar));
                cuentaRepository.save(cuenta);

                // 2. Registrar la transacción de débito de comisión
                Transaccion transaccion = new Transaccion();
                transaccion.setCuenta(cuenta);
                transaccion.setTipo(TipoTransaccion.DEBITO_COMISION);
                transaccion.setMonto(comisionADebitar);
                transaccion.setEstado(EstadoTransaccion.COMPLETADA);
                transaccion.setFechaHora(LocalDateTime.now());

                transaccionRepository.save(transaccion);
                procesadas++;

                log.info("Comisión de ${} debitada exitosamente en la cuenta CBU: {}", comisionADebitar, cuenta.getCbu());
            }
        }

        log.info("========== LIQUIDACIÓN FINALIZADA. TOTAL CUENTAS PROCESADAS: {} ==========", procesadas);
    }
}