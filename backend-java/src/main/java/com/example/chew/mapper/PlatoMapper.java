
package com.example.chew.mapper;

import com.example.chew.dto.PlatoRequestDTO;
import com.example.chew.dto.PlatoResponseDTO;
import com.example.chew.entity.Plato;
import org.springframework.stereotype.Component;

@Component
public class PlatoMapper {

    // Convertir Entity a ResponseDTO
    public PlatoResponseDTO toResponseDTO(Plato plato) {

        if (plato == null) {
            return null;
        }

        PlatoResponseDTO dto = new PlatoResponseDTO();

        dto.setIdPlato(plato.getIdPlato());
        dto.setCodigoPlato(plato.getCodigoPlato());
        dto.setNombre(plato.getNombre());
        dto.setDescripcion(plato.getDescripcion());
        dto.setCategoria(plato.getCategoria());
        dto.setPrecio(plato.getPrecio());
        dto.setCantidadDePlatos(plato.getCantidadDePlatos());
        dto.setDisponibleLlevar(plato.getDisponibleLlevar());
        dto.setActivo(plato.getActivo());
        dto.setImagen(plato.getImagen());

        return dto;
    }

    // Convertir RequestDTO a Entity
    public Plato toEntity(PlatoRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Plato plato = new Plato();

        plato.setCodigoPlato(dto.getCodigoPlato());
        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setCategoria(dto.getCategoria());
        plato.setPrecio(dto.getPrecio());
        plato.setCantidadDePlatos(dto.getCantidadDePlatos());
        plato.setDisponibleLlevar(dto.getDisponibleLlevar());
        plato.setActivo(dto.getActivo());
        plato.setImagen(dto.getImagen());

        return plato;
    }
    
    // Actualizar los datos de una entidad existente
    public void actualizarEntity(PlatoRequestDTO dto, Plato plato) {

        plato.setCodigoPlato(dto.getCodigoPlato());
        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setCategoria(dto.getCategoria());
        plato.setPrecio(dto.getPrecio());
        plato.setCantidadDePlatos(dto.getCantidadDePlatos());
        plato.setDisponibleLlevar(dto.getDisponibleLlevar());
        plato.setActivo(dto.getActivo());
        plato.setImagen(dto.getImagen());
    }

}
