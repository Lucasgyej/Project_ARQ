package com.banco.tp2_avance.service;

import com.banco.tp2_avance.dto.ClienteRequestDto;
import com.banco.tp2_avance.dto.ClienteResponseDto;
import com.banco.tp2_avance.model.Cliente;
import com.banco.tp2_avance.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDto registrarCliente(ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombreRazonSocial(dto.getNombreRazonSocial());
        cliente.setCuil(dto.getCuil());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());

        Cliente guardado = clienteRepository.save(cliente);

        return new ClienteResponseDto(
                guardado.getId(),
                guardado.getNombreRazonSocial(),
                guardado.getCuil(),
                guardado.getEmail()
        );
    }
}