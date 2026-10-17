
package com.example.chew.service;

import com.example.chew.dto.PlatoRequestDTO;
import com.example.chew.dto.PlatoResponseDTO;
import java.util.List;
import java.util.Optional;

public interface PlatoService {

    List<PlatoResponseDTO> listarTodosDTO();

    Optional<PlatoResponseDTO> buscarPorId(Integer id);

    PlatoResponseDTO registrarPlato(PlatoRequestDTO dto);

    Optional<PlatoResponseDTO> actualizarPlato(
            Integer id,
            PlatoRequestDTO dto
    );
    
    // Obtener solamente los platos activos
    List<PlatoResponseDTO> listarActivosDTO();

    // Desactivar un plato sin eliminarlo fisicamente
    boolean desactivarPlato(Integer id);

}
