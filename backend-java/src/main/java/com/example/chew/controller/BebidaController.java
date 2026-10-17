
package com.example.chew.controller;

import com.example.chew.dto.BebidaRequestDTO;
import com.example.chew.dto.BebidaResponseDTO;
import com.example.chew.service.BebidaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bebidas")
public class BebidaController {

    private final BebidaService bebidaService;

    public BebidaController(BebidaService bebidaService) {
        this.bebidaService = bebidaService;
    }

    // GET - Listar todas las bebidas
    @GetMapping
    public ResponseEntity<List<BebidaResponseDTO>> listarTodas() {

        return ResponseEntity.ok(
                bebidaService.listarTodasDTO()
        );
    }

    // GET - Listar bebidas activas
    @GetMapping("/activas")
    public ResponseEntity<List<BebidaResponseDTO>> listarActivas() {

        return ResponseEntity.ok(
                bebidaService.listarActivasDTO()
        );
    }

    // GET - Buscar bebida por ID
    @GetMapping("/{id}")
    public ResponseEntity<BebidaResponseDTO> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.of(
                bebidaService.buscarPorId(id)
        );
    }

    // POST - Registrar bebida
    @PostMapping
    public ResponseEntity<BebidaResponseDTO> registrarBebida(
            @Valid @RequestBody BebidaRequestDTO dto) {

        BebidaResponseDTO guardada =
                bebidaService.registrarBebida(dto);

        URI ubicacion = URI.create(
                "/api/bebidas/" + guardada.getIdBebida()
        );

        return ResponseEntity
                .created(ubicacion)
                .body(guardada);
    }

    // PUT - Actualizar bebida
    @PutMapping("/{id}")
    public ResponseEntity<BebidaResponseDTO> actualizarBebida(
            @PathVariable Integer id,
            @Valid @RequestBody BebidaRequestDTO dto) {

        return ResponseEntity.of(
                bebidaService.actualizarBebida(id, dto)
        );
    }

    // DELETE - Desactivar bebida
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarBebida(
            @PathVariable Integer id) {

        boolean desactivada =
                bebidaService.desactivarBebida(id);

        if (!desactivada) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
