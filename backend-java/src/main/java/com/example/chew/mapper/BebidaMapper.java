
package com.example.chew.mapper;

import com.example.chew.dto.BebidaRequestDTO;
import com.example.chew.dto.BebidaResponseDTO;
import com.example.chew.entity.Bebida;
import org.springframework.stereotype.Component;

@Component
public class BebidaMapper {

    // Convertir entidad a DTO de respuesta
    public BebidaResponseDTO toResponseDTO(Bebida bebida) {

        if (bebida == null) {
            return null;
        }

        BebidaResponseDTO dto = new BebidaResponseDTO();

        dto.setIdBebida(bebida.getIdBebida());
        dto.setCodigoBebida(bebida.getCodigoBebida());
        dto.setNombre(bebida.getNombre());
        dto.setDescripcion(bebida.getDescripcion());
        dto.setCategoria(bebida.getCategoria());
        dto.setPrecio(bebida.getPrecio());
        dto.setCantidadDeBebidas(
                bebida.getCantidadDeBebidas());
        dto.setActivo(bebida.getActivo());
        dto.setImagen(bebida.getImagen());

        return dto;
    }

    // Convertir DTO de solicitud a entidad nueva
    public Bebida toEntity(BebidaRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Bebida bebida = new Bebida();

        bebida.setCodigoBebida(dto.getCodigoBebida());
        bebida.setNombre(dto.getNombre());
        bebida.setDescripcion(dto.getDescripcion());
        bebida.setCategoria(dto.getCategoria());
        bebida.setPrecio(dto.getPrecio());
        bebida.setCantidadDeBebidas(
                dto.getCantidadDeBebidas());
        bebida.setActivo(dto.getActivo());
        bebida.setImagen(dto.getImagen());

        return bebida;
    }

    // Actualizar los datos de una entidad existente
    public void actualizarEntity(
            BebidaRequestDTO dto,
            Bebida bebida) {

        bebida.setCodigoBebida(dto.getCodigoBebida());
        bebida.setNombre(dto.getNombre());
        bebida.setDescripcion(dto.getDescripcion());
        bebida.setCategoria(dto.getCategoria());
        bebida.setPrecio(dto.getPrecio());
        bebida.setCantidadDeBebidas(
                dto.getCantidadDeBebidas());
        bebida.setActivo(dto.getActivo());
        bebida.setImagen(dto.getImagen());
    }
}
