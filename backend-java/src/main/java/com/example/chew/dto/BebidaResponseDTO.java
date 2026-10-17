
package com.example.chew.dto;

import java.math.BigDecimal;

public class BebidaResponseDTO {

    private Integer idBebida;

    private String codigoBebida;

    private String nombre;

    private String descripcion;

    private String categoria;

    private BigDecimal precio;

    private Integer cantidadDeBebidas;

    private Boolean activo;

    private String imagen;

    public Integer getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(Integer idBebida) {
        this.idBebida = idBebida;
    }

    public String getCodigoBebida() {
        return codigoBebida;
    }

    public void setCodigoBebida(String codigoBebida) {
        this.codigoBebida = codigoBebida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCantidadDeBebidas() {
        return cantidadDeBebidas;
    }

    public void setCantidadDeBebidas(Integer cantidadDeBebidas) {
        this.cantidadDeBebidas = cantidadDeBebidas;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
    
}
