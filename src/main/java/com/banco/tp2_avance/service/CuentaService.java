package com.banco.tp2_avance.service;

import com.banco.tp2_avance.dto.*;

public interface CuentaService {
    CuentaResponseDto crearCuenta(CuentaRequestDto dto);
    CuentaResponseDto obtenerPorCbu(String cbu);
    TransferenciaResponseDto transferir(TransferenciaRequestDto dto);
    void realizarExtraccion(ExtraccionRequestDto dto);
}