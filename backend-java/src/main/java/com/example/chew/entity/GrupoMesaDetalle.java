
package com.example.chew.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grupo_mesa_detalle")
public class GrupoMesaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo_mesa_detalle")
    private Integer idGrupoMesaDetalle;

    @Column(name = "id_grupo_mesa", nullable = false)
    private Integer idGrupoMesa;

    @Column(name = "id_mesa", nullable = false)
    private Integer idMesa;

    public GrupoMesaDetalle() {
    }

    public Integer getIdGrupoMesaDetalle() {
        return idGrupoMesaDetalle;
    }

    public void setIdGrupoMesaDetalle(
            Integer idGrupoMesaDetalle) {

        this.idGrupoMesaDetalle = idGrupoMesaDetalle;
    }

    public Integer getIdGrupoMesa() {
        return idGrupoMesa;
    }

    public void setIdGrupoMesa(Integer idGrupoMesa) {
        this.idGrupoMesa = idGrupoMesa;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }
}
