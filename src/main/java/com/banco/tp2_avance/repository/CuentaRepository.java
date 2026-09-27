package com.banco.tp2_avance.repository;

import com.banco.tp2_avance.enums.EstadoCuenta;
import com.banco.tp2_avance.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByCbu(String cbu);

    Optional<Cuenta> findByAlias(String alias);

    List<Cuenta> findByEstado(EstadoCuenta estado);
}