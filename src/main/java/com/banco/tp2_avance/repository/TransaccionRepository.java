package com.banco.tp2_avance.repository;

import com.banco.tp2_avance.enums.EstadoTransaccion;
import com.banco.tp2_avance.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    List<Transaccion> findByCuentaIdAndEstado(Long cuentaId, EstadoTransaccion estado);

    List<Transaccion> findByCuentaIdAndFechaHoraBetween(Long cuentaId, LocalDateTime inicio, LocalDateTime fin);
}