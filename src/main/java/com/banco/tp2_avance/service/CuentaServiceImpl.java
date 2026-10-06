package com.banco.tp2_avance.service;

import com.banco.tp2_avance.dto.*;
import com.banco.tp2_avance.enums.EstadoCuenta;
import com.banco.tp2_avance.enums.EstadoTransaccion;
import com.banco.tp2_avance.enums.RolCliente;
import com.banco.tp2_avance.enums.TipoTransaccion;
import com.banco.tp2_avance.exception.LimiteDiarioSuperadoException;
import com.banco.tp2_avance.exception.OperacionNoPermitidaException;
import com.banco.tp2_avance.exception.RecursoNoEncontradoException;
import com.banco.tp2_avance.exception.SaldoInsuficienteException;
import com.banco.tp2_avance.model.CajaDeAhorro;
import com.banco.tp2_avance.model.Cliente;
import com.banco.tp2_avance.model.Cuenta;
import com.banco.tp2_avance.model.Transaccion;
import com.banco.tp2_avance.repository.ClienteRepository;
import com.banco.tp2_avance.repository.CuentaRepository;
import com.banco.tp2_avance.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CuentaServiceImpl implements CuentaService {

    private static final BigDecimal LIMITE_TITULAR = new BigDecimal("100000.00");
    private static final BigDecimal LIMITE_ADHERENTE = new BigDecimal("70000.00");

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final TransaccionRepository transaccionRepository;


    public CuentaServiceImpl(CuentaRepository cuentaRepository,
                             ClienteRepository clienteRepository,
                             TransaccionRepository transaccionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    @Transactional
    public CuentaResponseDto crearCuenta(CuentaRequestDto dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente con ID: " + dto.getClienteId()));

        CajaDeAhorro cuenta = new CajaDeAhorro();
        cuenta.setCbu(dto.getCbu());
        cuenta.setAlias(dto.getAlias());
        cuenta.setSaldoOperativo(dto.getSaldoInicial());
        cuenta.setEstado(EstadoCuenta.ACTIVA);
        cuenta.getCotitulares().add(cliente);

        Cuenta guardada = cuentaRepository.save(cuenta);

        return new CuentaResponseDto(
                guardada.getId(),
                guardada.getCbu(),
                guardada.getAlias(),
                guardada.getSaldoOperativo(),
                guardada.getEstado().name()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponseDto obtenerPorCbu(String cbu) {
        Cuenta cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la cuenta con CBU: " + cbu));

        return new CuentaResponseDto(
                cuenta.getId(),
                cuenta.getCbu(),
                cuenta.getAlias(),
                cuenta.getSaldoOperativo(),
                cuenta.getEstado().name()
        );
    }

    @Override
    @Transactional
    public TransferenciaResponseDto transferir(TransferenciaRequestDto dto) {
        Cuenta origen = cuentaRepository.findByCbu(dto.getCbuOrigen())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta de origen no encontrada con CBU: " + dto.getCbuOrigen()));

        Cuenta destino = cuentaRepository.findByCbu(dto.getCbuDestino())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta de destino no encontrada con CBU: " + dto.getCbuDestino()));

        if (origen.getSaldoOperativo().compareTo(dto.getMonto()) < 0) {
            throw new SaldoInsuficienteException("La cuenta de origen no posee saldo suficiente para realizar la transferencia.");
        }

        origen.setSaldoOperativo(origen.getSaldoOperativo().subtract(dto.getMonto()));
        destino.setSaldoOperativo(destino.getSaldoOperativo().add(dto.getMonto()));

        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        Transaccion txSalida = new Transaccion(
                LocalDateTime.now(),
                dto.getMonto(),
                TipoTransaccion.TRANSFERENCIA_ENVIADA,
                EstadoTransaccion.COMPLETADA,
                origen
        );

        Transaccion txEntrada = new Transaccion(
                LocalDateTime.now(),
                dto.getMonto(),
                TipoTransaccion.TRANSFERENCIA_RECIBIDA,
                EstadoTransaccion.COMPLETADA,
                destino
        );

        transaccionRepository.save(txSalida);
        transaccionRepository.save(txEntrada);

        return new TransferenciaResponseDto(
                "Transferencia ejecutada con éxito",
                dto.getCbuOrigen(),
                dto.getCbuDestino(),
                dto.getMonto(),
                LocalDateTime.now()
        );
    }
    @Override
    @Transactional
    public void realizarExtraccion(ExtraccionRequestDto dto) {

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada"));


        if (cliente.getRol() == RolCliente.ADHERENTE) {
            if (cliente.getCuentaTitular() == null || !cliente.getCuentaTitular().getId().equals(cuenta.getId())) {
                throw new OperacionNoPermitidaException("El adherente no está autorizado a operar sobre esta cuenta.");
            }
        }


        BigDecimal limiteDiario = (cliente.getRol() == RolCliente.TITULAR) ? LIMITE_TITULAR : LIMITE_ADHERENTE;


        LocalDateTime inicioDelDia = LocalDate.now().atStartOfDay();
        BigDecimal extraidoHoy = transaccionRepository.sumExtraccionesDelDia(
                cliente.getId(),
                TipoTransaccion.EXTRACCION,
                EstadoTransaccion.COMPLETADA,
                inicioDelDia
        );

        BigDecimal acumuladoProyectado = extraidoHoy.add(dto.getMonto());
        if (acumuladoProyectado.compareTo(limiteDiario) > 0) {
            throw new LimiteDiarioSuperadoException(
                    "Límite diario superado. Límite: $" + limiteDiario +
                            ". Acumulado hoy: $" + extraidoHoy +
                            ". Intentó extraer: $" + dto.getMonto()
            );
        }


        if (cuenta.getSaldoOperativo().compareTo(dto.getMonto()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta para realizar la extracción.");
        }


        cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().subtract(dto.getMonto()));
        cuentaRepository.save(cuenta);


        Transaccion transaccion = new Transaccion();
        transaccion.setCuenta(cuenta);
        transaccion.setCliente(cliente);
        transaccion.setTipo(TipoTransaccion.EXTRACCION);
        transaccion.setMonto(dto.getMonto());
        transaccion.setEstado(EstadoTransaccion.COMPLETADA);
        transaccion.setFechaHora(LocalDateTime.now());
        transaccionRepository.save(transaccion);
    }
}