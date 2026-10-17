
package com.example.chew.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grupos_mesa")
public class GrupoMesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo_mesa")
    private Integer idGrupoMesa;

    @Column(name = "nombre_grupo")
    private String nombreGrupo;

    @Column(name = "mesa_principal")
    private Integer mesaPrincipal;

    @Column(
        name = "fecha_creacion",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime fechaCreacion;

    @Column(name = "estado", nullable = false)
    private String estado = "activo";

    public GrupoMesa() {
    }

    public Integer getIdGrupoMesa() {
        return idGrupoMesa;
    }

    public void setIdGrupoMesa(Integer idGrupoMesa) {
        this.idGrupoMesa = idGrupoMesa;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Integer getMesaPrincipal() {
        return mesaPrincipal;
    }

    public void setMesaPrincipal(Integer mesaPrincipal) {
        this.mesaPrincipal = mesaPrincipal;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
