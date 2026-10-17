
package com.example.chew.service.impl;

import com.example.chew.dto.PlatoRequestDTO;
import com.example.chew.dto.PlatoResponseDTO;
import com.example.chew.entity.Plato;
import com.example.chew.exception.CodigoPlatoDuplicadoException;
import com.example.chew.mapper.PlatoMapper;
import com.example.chew.repository.PlatoRepository;
import com.example.chew.service.PlatoService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;
    private final PlatoMapper platoMapper;

    public PlatoServiceImpl(
            PlatoRepository platoRepository,
            PlatoMapper platoMapper) {

        this.platoRepository = platoRepository;
        this.platoMapper = platoMapper;
    }

    // Listar todos los platos
    @Override
    public List<PlatoResponseDTO> listarTodosDTO() {

        return platoRepository.findAll()
                .stream()
                .map(platoMapper::toResponseDTO)
                .toList();
    }

    // Buscar un plato por ID
    @Override
    public Optional<PlatoResponseDTO> buscarPorId(Integer id) {

        return platoRepository.findById(id)
                .map(platoMapper::toResponseDTO);
    }

    // Registrar un plato nuevo
    @Override
    @Transactional
    public PlatoResponseDTO registrarPlato(PlatoRequestDTO dto) {

        if (platoRepository.existsByCodigoPlato(dto.getCodigoPlato())) {

            throw new CodigoPlatoDuplicadoException(
                    dto.getCodigoPlato()
            );
        }

        Plato plato = platoMapper.toEntity(dto);

        Plato platoGuardado = platoRepository.save(plato);

        return platoMapper.toResponseDTO(platoGuardado);
    }

    // Actualizar un plato existente
    @Override
    @Transactional
    public Optional<PlatoResponseDTO> actualizarPlato(
            Integer id,
            PlatoRequestDTO dto) {

        return platoRepository.findById(id)
                .map(plato -> {

                    // Verificar si otro plato utiliza el codigo
                    if (platoRepository
                            .existsByCodigoPlatoAndIdPlatoNot(
                                    dto.getCodigoPlato(), id)) {

                        throw new CodigoPlatoDuplicadoException(
                                dto.getCodigoPlato()
                        );
                    }

                    // Actualizar los datos de la entidad existente
                    platoMapper.actualizarEntity(dto, plato);

                    // Guardar los cambios en Neon
                    Plato platoActualizado =
                            platoRepository.save(plato);

                    // Devolver los datos actualizados
                    return platoMapper.toResponseDTO(platoActualizado);
                });
    }
    
    // Obtener los platos activos
    @Override
    @Transactional(readOnly = true)
    public List<PlatoResponseDTO> listarActivosDTO() {

        return platoRepository.findByActivoTrue()
                .stream()
                .map(platoMapper::toResponseDTO)
                .toList();
    }

    // Desactivar un plato mediante eliminacion logica
    @Override
    @Transactional
    public boolean desactivarPlato(Integer id) {

        return platoRepository.findById(id)
                .map(plato -> {

                    // Cambiar el estado sin borrar el registro
                    plato.setActivo(false);

                    // Guardar el cambio en PostgreSQL
                    platoRepository.save(plato);

                    return true;
                })
                .orElse(false);
    }

}
