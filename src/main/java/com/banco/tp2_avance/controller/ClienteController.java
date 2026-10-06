package com.banco.tp2_avance.controller;

import com.banco.tp2_avance.dto.ClienteRequestDto;
import com.banco.tp2_avance.dto.ClienteResponseDto;
import com.banco.tp2_avance.dto.AdherenteRequestDto;
import com.banco.tp2_avance.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> registrarCliente(@Valid @RequestBody ClienteRequestDto dto) {
        ClienteResponseDto respuesta = clienteService.registrarCliente(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    @PostMapping("/adherentes")
    public ResponseEntity<ClienteResponseDto> registrarAdherente(@Valid @RequestBody AdherenteRequestDto dto) {
        ClienteResponseDto respuesta = clienteService.registrarAdherente(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    @GetMapping("/activar")
    public ResponseEntity<Map<String, String>> activarCliente(@RequestParam String token) {
        clienteService.activarCliente(token);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Cuenta activada exitosamente. Ya podés operar con el banco.");
        return ResponseEntity.ok(respuesta);
    }
}