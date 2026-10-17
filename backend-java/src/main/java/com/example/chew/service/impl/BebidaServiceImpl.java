
package com.example.chew.service.impl;

import com.example.chew.dto.BebidaRequestDTO;
import com.example.chew.dto.BebidaResponseDTO;
import com.example.chew.entity.Bebida;
import com.example.chew.exception.CodigoBebidaDuplicadoException;
import com.example.chew.mapper.BebidaMapper;
import com.example.chew.repository.BebidaRepository;
import com.example.chew.service.BebidaService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BebidaServiceImpl implements BebidaService {

    private final BebidaRepository bebidaRepository;
    private final BebidaMapper bebidaMapper;

    public BebidaServiceImpl(
            BebidaRepository bebidaRepository,
            BebidaMapper bebidaMapper) {

        this.bebidaRepository = bebidaRepository;
        this.bebidaMapper = bebidaMapper;
    }

    // GET - Listar todas las bebidas
    @Override
    @Transactional(readOnly = true)
    public List<BebidaResponseDTO> listarTodasDTO() {

        return bebidaRepository.findAll()
                .stream()
                .map(bebidaMapper::toResponseDTO)
                .toList();
    }

    // GET - Listar solamente las bebidas activas
    @Override
    @Transactional(readOnly = true)
    public List<BebidaResponseDTO> listarActivasDTO() {

        return bebidaRepository.findByActivoTrue()
                .stream()
                .map(bebidaMapper::toResponseDTO)
                .toList();
    }

    // GET - Buscar bebida por ID
    @Override
    @Transactional(readOnly = true)
    public Optional<BebidaResponseDTO> buscarPorId(
            Integer id) {

        return bebidaRepository.findById(id)
                .map(bebidaMapper::toResponseDTO);
    }

    // POST - Registrar bebida
    @Override
    @Transactional
    public BebidaResponseDTO registrarBebida(
            BebidaRequestDTO dto) {

        verificarCodigoDuplicado(
                dto.getCodigoBebida(), null
        );

        Bebida bebida = bebidaMapper.toEntity(dto);

        Bebida guardada = bebidaRepository.save(bebida);

        return bebidaMapper.toResponseDTO(guardada);
    }

    // PUT - Actualizar bebida
    @Override
    @Transactional
    public Optional<BebidaResponseDTO> actualizarBebida(
            Integer id,
            BebidaRequestDTO dto) {

        return bebidaRepository.findById(id)
                .map(bebida -> {

                    verificarCodigoDuplicado(
                            dto.getCodigoBebida(), id
                    );

                    bebidaMapper.actualizarEntity(
                            dto, bebida
                    );

                    Bebida actualizada =
                            bebidaRepository.save(bebida);

                    return bebidaMapper.toResponseDTO(
                            actualizada
                    );
                });
    }

    // DELETE - Desactivacion logica
    @Override
    @Transactional
    public boolean desactivarBebida(Integer id) {

        return bebidaRepository.findById(id)
                .map(bebida -> {

                    bebida.setActivo(false);

                    bebidaRepository.save(bebida);

                    return true;
                })
                .orElse(false);
    }

    // Validar codigos duplicados
    private void verificarCodigoDuplicado(
            String codigoBebida,
            Integer idActual) {

        // El codigo es opcional en nuestra base de datos
        if (codigoBebida == null) {
            return;
        }

        boolean duplicado;

        if (idActual == null) {

            // Validacion para un nuevo registro
            duplicado = bebidaRepository
                    .existsByCodigoBebida(codigoBebida);

        } else {

            // Validacion al actualizar un registro
            duplicado = bebidaRepository
                    .existsByCodigoBebidaAndIdBebidaNot(
                            codigoBebida,
                            idActual
                    );
        }

        if (duplicado) {
            throw new CodigoBebidaDuplicadoException(
                    codigoBebida
            );
        }
    }
}
