package com.banco.tp2_avance.model;

import com.banco.tp2_avance.enums.EstadoCliente;
import com.banco.tp2_avance.enums.Parentesco;
import com.banco.tp2_avance.enums.RolCliente;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombreRazonSocial;

    @Column(nullable = false, unique = true, length = 11)
    private String cuil;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    // --- Campos agregados para Grupo Familiar y Rol ---
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolCliente rol = RolCliente.TITULAR;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Parentesco parentesco = Parentesco.NINGUNO;

    // Si es ADHERENTE, se vincula a la cuenta específica del titular sobre la cual operará
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_titular_id")
    private Cuenta cuentaTitular;

    // --- Campos de activación (Módulo B) ---
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoCliente estado = EstadoCliente.PENDIENTE_ACTIVACION;

    @Column(length = 64)
    private String tokenActivacion;

    private LocalDateTime fechaExpiracionToken;

    // Relación original de cuentas
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cliente_cuenta",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "cuenta_id")
    )
    private Set<Cuenta> cuentas = new HashSet<>();

    public Cliente() {
    }

    public Cliente(String nombreRazonSocial, String cuil, String email, String telefono, String direccion) {
        this.nombreRazonSocial = nombreRazonSocial;
        this.cuil = cuil;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public void setNombreRazonSocial(String nombreRazonSocial) { this.nombreRazonSocial = nombreRazonSocial; }

    public String getCuil() { return cuil; }
    public void setCuil(String cuil) { this.cuil = cuil; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public RolCliente getRol() { return rol; }
    public void setRol(RolCliente rol) { this.rol = rol; }

    public Parentesco getParentesco() { return parentesco; }
    public void setParentesco(Parentesco parentesco) { this.parentesco = parentesco; }

    public Cuenta getCuentaTitular() { return cuentaTitular; }
    public void setCuentaTitular(Cuenta cuentaTitular) { this.cuentaTitular = cuentaTitular; }

    public EstadoCliente getEstado() { return estado; }
    public void setEstado(EstadoCliente estado) { this.estado = estado; }

    public String getTokenActivacion() { return tokenActivacion; }
    public void setTokenActivacion(String tokenActivacion) { this.tokenActivacion = tokenActivacion; }

    public LocalDateTime getFechaExpiracionToken() { return fechaExpiracionToken; }
    public void setFechaExpiracionToken(LocalDateTime fechaExpiracionToken) { this.fechaExpiracionToken = fechaExpiracionToken; }

    public Set<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(Set<Cuenta> cuentas) { this.cuentas = cuentas; }
}