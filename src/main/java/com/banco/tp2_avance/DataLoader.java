package com.banco.tp2_avance;

import com.banco.tp2_avance.enums.EstadoCuenta;
import com.banco.tp2_avance.enums.EstadoTransaccion;
import com.banco.tp2_avance.enums.TipoTransaccion;
import com.banco.tp2_avance.model.*;
import com.banco.tp2_avance.repository.ClienteRepository;
import com.banco.tp2_avance.repository.CuentaRepository;
import com.banco.tp2_avance.repository.TransaccionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    public DataLoader(ClienteRepository clienteRepository,
                      CuentaRepository cuentaRepository,
                      TransaccionRepository transaccionRepository) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clienteRepository.count() > 0) {
            return;
        }

        // 1. Crear Cliente
        Cliente cliente = new Cliente(
                "Juan Perez",
                "20401234567",
                "juan.perez@email.com",
                "388-154987654",
                "Av. Bolivia 1239, Jujuy"
        );
        clienteRepository.save(cliente);


        CajaDeAhorro cajaAhorro = new CajaDeAhorro(
                "1234567890123456789012",
                "MATE.TERMO.CASA",
                new BigDecimal("150000.00"),
                EstadoCuenta.ACTIVA,
                new BigDecimal("42.50"),
                5
        );
        cajaAhorro.getCotitulares().add(cliente);
        cuentaRepository.save(cajaAhorro);


        cliente.getCuentas().add(cajaAhorro);
        clienteRepository.save(cliente);


        Transaccion transaccion = new Transaccion(
                LocalDateTime.now(),
                new BigDecimal("15000.00"),
                TipoTransaccion.DEPOSITO,
                EstadoTransaccion.COMPLETADA,
                cajaAhorro
        );
        transaccionRepository.save(transaccion);

        System.out.println("=== DATOS DE PRUEBA CARGADOS EXITOSAMENTE ===");
    }
}