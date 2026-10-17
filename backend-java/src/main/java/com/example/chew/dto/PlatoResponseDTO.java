
package com.example.chew.dto;

import java.math.BigDecimal;

public class PlatoResponseDTO {

    private Integer idPlato;
    private String codigoPlato;
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precio;
    private Integer cantidadDePlatos;
    private Boolean disponibleLlevar;
    private Boolean activo;
    private String imagen;

    public PlatoResponseDTO() {
    }

    public Integer getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
    }

    public String getCodigoPlato() {
        return codigoPlato;
    }

    public void setCodigoPlato(String codigoPlato) {
        this.codigoPlato = codigoPlato;
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

    public Integer getCantidadDePlatos() {
        return cantidadDePlatos;
    }

    public void setCantidadDePlatos(Integer cantidadDePlatos) {
        this.cantidadDePlatos = cantidadDePlatos;
    }

    public Boolean getDisponibleLlevar() {
        return disponibleLlevar;
    }

    public void setDisponibleLlevar(Boolean disponibleLlevar) {
        this.disponibleLlevar = disponibleLlevar;
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
