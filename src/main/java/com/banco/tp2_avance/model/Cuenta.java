package com.banco.tp2_avance.model;
import com.banco.tp2_avance.enums.EstadoCuenta;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cuentas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cuenta extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //UUD para generar un id con 20 caracteres

    @Column(nullable = false, unique = true, length = 22)
    private String cbu;

    @Column(nullable = false, unique = true, length = 50)
    private String alias;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoOperativo = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCuenta estado = EstadoCuenta.ACTIVA;

    @ManyToMany(mappedBy = "cuentas", fetch = FetchType.LAZY)
    private Set<Cliente> cotitulares = new HashSet<>();

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaccion> transacciones = new ArrayList<>();

    public Cuenta() {
    }

    public Cuenta(String cbu, String alias, BigDecimal saldoOperativo, EstadoCuenta estado) {
        this.cbu = cbu;
        this.alias = alias;
        this.saldoOperativo = saldoOperativo;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCbu() { return cbu; }
    public void setCbu(String cbu) { this.cbu = cbu; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public BigDecimal getSaldoOperativo() { return saldoOperativo; }
    public void setSaldoOperativo(BigDecimal saldoOperativo) { this.saldoOperativo = saldoOperativo; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }

    public Set<Cliente> getCotitulares() { return cotitulares; }
    public void setCotitulares(Set<Cliente> cotitulares) { this.cotitulares = cotitulares; }

    public List<Transaccion> getTransacciones() { return transacciones; }
    public void setTransacciones(List<Transaccion> transacciones) { this.transacciones = transacciones; }
}