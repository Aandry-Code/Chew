package com.example.chew.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.chew.dto.PlatoRequestDTO;
import com.example.chew.dto.PlatoResponseDTO;
import com.example.chew.service.PlatoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    // GET - Listar todos los platos
    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(platoService.listarTodosDTO());
    }

    // GET - Buscar un plato por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.of(platoService.buscarPorId(id));
    }

    // POST - Registrar un nuevo plato
    @PostMapping
    public ResponseEntity<PlatoResponseDTO> registrarPlato(
            @Valid @RequestBody PlatoRequestDTO dto) {

        PlatoResponseDTO platoRegistrado
                = platoService.registrarPlato(dto);

        URI ubicacion = URI.create(
                "/api/platos/" + platoRegistrado.getIdPlato()
        );

        return ResponseEntity
                .created(ubicacion)
                .body(platoRegistrado);
    }

    // PUT - Actualizar un plato existente
    @PutMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> actualizarPlato(
            @PathVariable Integer id,
            @Valid @RequestBody PlatoRequestDTO dto) {

        return ResponseEntity.of(
                platoService.actualizarPlato(id, dto)
        );
    }

    // GET - Consultar solamente los platos activos
    @GetMapping("/activos")
    public ResponseEntity<List<PlatoResponseDTO>> listarActivos() {

        return ResponseEntity.ok(
                platoService.listarActivosDTO()
        );
    }

    // DELETE - Desactivar un plato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPlato(
            @PathVariable Integer id) {

        boolean desactivado = platoService.desactivarPlato(id);

        if (!desactivado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
