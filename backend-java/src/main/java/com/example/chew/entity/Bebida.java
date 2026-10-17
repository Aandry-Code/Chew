
package com.example.chew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bebidas")
public class Bebida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bebida")
    private Integer idBebida;

    @Column(name = "codigo_bebida", length = 40)
    private String codigoBebida;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "cantidad_de_bebidas", nullable = false)
    private Integer cantidadDeBebidas;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "imagen", columnDefinition = "text")
    private String imagen;

    // getters y setters 

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
