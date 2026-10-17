
package com.example.chew.service;

import com.example.chew.dto.BebidaRequestDTO;
import com.example.chew.dto.BebidaResponseDTO;
import java.util.List;
import java.util.Optional;

public interface BebidaService {

    // Listar todas las bebidas
    List<BebidaResponseDTO> listarTodasDTO();

    // Listar solamente las bebidas activas
    List<BebidaResponseDTO> listarActivasDTO();

    // Buscar una bebida por su ID
    Optional<BebidaResponseDTO> buscarPorId(Integer id);

    // Registrar una bebida
    BebidaResponseDTO registrarBebida(
            BebidaRequestDTO dto
    );

    // Actualizar una bebida existente
    Optional<BebidaResponseDTO> actualizarBebida(
            Integer id,
            BebidaRequestDTO dto
    );

    // Desactivar una bebida
    boolean desactivarBebida(Integer id);
}
