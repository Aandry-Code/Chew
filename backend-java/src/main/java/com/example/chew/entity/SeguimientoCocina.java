
package com.example.chew.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento_cocina")
public class SeguimientoCocina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;

    @Column(
        name = "id_detalle_producto",
        nullable = false,
        unique = true
    )
    private Integer idDetalleProducto;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "recibido";

    @Column(
        name = "recibido_at",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime recibidoAt;

    @Column(name = "preparando_at")
    private LocalDateTime preparandoAt;

    @Column(name = "listo_at")
    private LocalDateTime listoAt;

    @Column(name = "entregado_at")
    private LocalDateTime entregadoAt;

    @Column(name = "cancelado_at")
    private LocalDateTime canceladoAt;

    public SeguimientoCocina() {
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Integer getIdDetalleProducto() {
        return idDetalleProducto;
    }

    public void setIdDetalleProducto(Integer idDetalleProducto) {
        this.idDetalleProducto = idDetalleProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getRecibidoAt() {
        return recibidoAt;
    }

    public LocalDateTime getPreparandoAt() {
        return preparandoAt;
    }

    public void setPreparandoAt(LocalDateTime preparandoAt) {
        this.preparandoAt = preparandoAt;
    }

    public LocalDateTime getListoAt() {
        return listoAt;
    }

    public void setListoAt(LocalDateTime listoAt) {
        this.listoAt = listoAt;
    }

    public LocalDateTime getEntregadoAt() {
        return entregadoAt;
    }

    public void setEntregadoAt(LocalDateTime entregadoAt) {
        this.entregadoAt = entregadoAt;
    }

    public LocalDateTime getCanceladoAt() {
        return canceladoAt;
    }

    public void setCanceladoAt(LocalDateTime canceladoAt) {
        this.canceladoAt = canceladoAt;
    }
}
