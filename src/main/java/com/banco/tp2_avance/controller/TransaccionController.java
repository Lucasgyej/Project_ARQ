package com.banco.tp2_avance.controller;

import com.banco.tp2_avance.dto.TransferenciaRequestDto;
import com.banco.tp2_avance.dto.TransferenciaResponseDto;
import com.banco.tp2_avance.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {

    private final CuentaService cuentaService;

    public TransaccionController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping("/transferir")
    public ResponseEntity<TransferenciaResponseDto> transferir(@Valid @RequestBody TransferenciaRequestDto dto) {
        TransferenciaResponseDto respuesta = cuentaService.transferir(dto);
        return ResponseEntity.ok(respuesta);
    }
}