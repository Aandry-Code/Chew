
package com.example.chew.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Integer idMesa;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numeroMesa;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "qr_token", length = 120)
    private String qrToken;

    @Column(name = "qr_activo", nullable = false)
    private Boolean qrActivo;

    public Mesa() {
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public Boolean getQrActivo() {
        return qrActivo;
    }

    public void setQrActivo(Boolean qrActivo) {
        this.qrActivo = qrActivo;
    }
}
