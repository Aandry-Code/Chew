
package com.example.chew.controller;

import com.example.chew.dto.PedidoRequestDTO;
import com.example.chew.dto.PedidoResponseDTO;
import com.example.chew.service.PedidoService;

import jakarta.validation.Valid;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearPedido(
            @Valid @RequestBody PedidoRequestDTO solicitud) {

        PedidoResponseDTO resultado =
                pedidoService.crearPedidoMesa(solicitud);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "ok", true,
                        "data", resultado
                ));
    }
}
