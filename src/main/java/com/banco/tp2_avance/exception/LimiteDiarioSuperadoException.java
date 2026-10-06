package com.banco.tp2_avance.exception;

public class LimiteDiarioSuperadoException extends RuntimeException {
    public LimiteDiarioSuperadoException(String mensaje) {
        super(mensaje);
    }
}