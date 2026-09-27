package com.banco.tp2_avance.repository;

import com.banco.tp2_avance.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCuil(String cuil);

    Optional<Cliente> findByEmailIgnoreCase(String email);
}