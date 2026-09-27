package com.banco.tp2_avance.model;

import jakarta.persistence.*;
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

    public Set<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(Set<Cuenta> cuentas) { this.cuentas = cuentas; }
}