package com.banco.tp2_avance.controller;

import com.banco.tp2_avance.dto.CuentaRequestDto;
import com.banco.tp2_avance.dto.CuentaResponseDto;
import com.banco.tp2_avance.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    //requestbody -> agarra el JSON y lo convierte en un objeto CuentaRequestDto
    public ResponseEntity<CuentaResponseDto> crearCuenta(@Valid @RequestBody CuentaRequestDto dto) {
        CuentaResponseDto respuesta = cuentaService.crearCuenta(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping("/{cbu}")
    public ResponseEntity<CuentaResponseDto> obtenerPorCbu(@PathVariable String cbu) {
        CuentaResponseDto respuesta = cuentaService.obtenerPorCbu(cbu);
        return ResponseEntity.ok(respuesta);
    }
}