package com.banco.tp2_avance.repository;

import com.banco.tp2_avance.enums.EstadoTransaccion;
import com.banco.tp2_avance.enums.TipoTransaccion;
import com.banco.tp2_avance.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    List<Transaccion> findByCuentaIdAndEstado(Long cuentaId, EstadoTransaccion estado);

    List<Transaccion> findByCuentaIdAndFechaHoraBetween(Long cuentaId, LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM Transaccion t " +
            "WHERE t.cliente.id = :clienteId " +
            "AND t.tipo = :tipo " +
            "AND t.estado = :estado " +
            "AND t.fechaHora >= :desde")
    BigDecimal sumExtraccionesDelDia(
            @Param("clienteId") Long clienteId,
            @Param("tipo") TipoTransaccion tipo,
            @Param("estado") EstadoTransaccion estado,
            @Param("desde") LocalDateTime desde
    );
}