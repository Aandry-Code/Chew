
package com.example.chew.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "id_grupo_mesa")
    private Integer idGrupoMesa;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "tipo_pedido", nullable = false, length = 20)
    private String tipoPedido;

    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "telefono_llevar", length = 9)
    private String telefonoLlevar;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "pendiente";

    @Column(
        name = "fecha_creacion",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime fechaCreacion;

    @Column(name = "registrado_por")
    private Integer registradoPor;

    public Pedido() {
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdGrupoMesa() {
        return idGrupoMesa;
    }

    public void setIdGrupoMesa(Integer idGrupoMesa) {
        this.idGrupoMesa = idGrupoMesa;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoLlevar() {
        return telefonoLlevar;
    }

    public void setTelefonoLlevar(String telefonoLlevar) {
        this.telefonoLlevar = telefonoLlevar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Integer getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(Integer registradoPor) {
        this.registradoPor = registradoPor;
    }
}
