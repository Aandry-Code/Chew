
package com.example.chew.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class PlatoRequestDTO {

    @NotBlank(message = "El codigo del plato es obligatorio")
    @Size(max = 40, message = "El codigo no puede superar 40 caracteres")
    private String codigoPlato;

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    private String descripcion;

    @Size(max = 50, message = "La categoria no puede superar 50 caracteres")
    private String categoria;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener como maximo 8 enteros y 2 decimales")
    private BigDecimal precio;

    @NotNull(message = "La cantidad es obligatoria")
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private Integer cantidadDePlatos;

    @NotNull(message = "Debe indicar si esta disponible para llevar")
    private Boolean disponibleLlevar;

    @NotNull(message = "Debe indicar si el plato esta activo")
    private Boolean activo;

    private String imagen;

    public PlatoRequestDTO() {
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
