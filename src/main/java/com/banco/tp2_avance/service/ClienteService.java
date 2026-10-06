package com.banco.tp2_avance.service;

import com.banco.tp2_avance.dto.AdherenteRequestDto;
import com.banco.tp2_avance.dto.ClienteRequestDto;
import com.banco.tp2_avance.dto.ClienteResponseDto;
import com.banco.tp2_avance.enums.EstadoCliente;
import com.banco.tp2_avance.enums.RolCliente;
import com.banco.tp2_avance.event.ClienteRegistradoEvent;
import com.banco.tp2_avance.exception.RecursoNoEncontradoException;
import com.banco.tp2_avance.model.Cliente;
import com.banco.tp2_avance.model.Cuenta;
import com.banco.tp2_avance.repository.ClienteRepository;
import com.banco.tp2_avance.repository.CuentaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ClienteService(ClienteRepository clienteRepository,
                          CuentaRepository cuentaRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ClienteResponseDto registrarCliente(ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombreRazonSocial(dto.getNombreRazonSocial());
        cliente.setCuil(dto.getCuil());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());


        String token = UUID.randomUUID().toString();
        cliente.setRol(RolCliente.TITULAR);
        cliente.setEstado(EstadoCliente.PENDIENTE_ACTIVACION);
        cliente.setTokenActivacion(token);
        cliente.setFechaExpiracionToken(LocalDateTime.now().plusHours(24));

        Cliente guardado = clienteRepository.save(cliente);


        eventPublisher.publishEvent(new ClienteRegistradoEvent(
                guardado.getEmail(),
                guardado.getNombreRazonSocial(),
                token
        ));

        return new ClienteResponseDto(
                guardado.getId(),
                guardado.getNombreRazonSocial(),
                guardado.getCuil(),
                guardado.getEmail()
        );
    }

    @Transactional
    public ClienteResponseDto registrarAdherente(AdherenteRequestDto dto) {
        Cuenta cuentaTitular = cuentaRepository.findById(dto.getCuentaTitularId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta titular no encontrada con ID: " + dto.getCuentaTitularId()));

        Cliente adherente = new Cliente();
        adherente.setNombreRazonSocial(dto.getNombreRazonSocial());
        adherente.setCuil(dto.getCuil());
        adherente.setEmail(dto.getEmail());
        adherente.setRol(RolCliente.ADHERENTE);
        adherente.setParentesco(dto.getParentesco());
        adherente.setCuentaTitular(cuentaTitular);
        adherente.setEstado(EstadoCliente.ACTIVO);

        Cliente guardado = clienteRepository.save(adherente);

        return new ClienteResponseDto(
                guardado.getId(),
                guardado.getNombreRazonSocial(),
                guardado.getCuil(),
                guardado.getEmail()
        );
    }
}