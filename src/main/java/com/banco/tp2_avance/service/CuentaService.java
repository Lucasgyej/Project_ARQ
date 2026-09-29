package com.banco.tp2_avance.service;

import com.banco.tp2_avance.dto.CuentaRequestDto;
import com.banco.tp2_avance.dto.CuentaResponseDto;
import com.banco.tp2_avance.dto.TransferenciaRequestDto;
import com.banco.tp2_avance.dto.TransferenciaResponseDto;

public interface CuentaService {
    CuentaResponseDto crearCuenta(CuentaRequestDto dto);
    CuentaResponseDto obtenerPorCbu(String cbu);
    TransferenciaResponseDto transferir(TransferenciaRequestDto dto);
}