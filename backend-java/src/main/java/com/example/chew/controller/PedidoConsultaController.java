package com.example.chew.controller;

import com.example.chew.entity.Mesa;
import com.example.chew.repository.MesaRepository;
import com.example.chew.service.PedidoConsultaService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos/mesa")
public class PedidoConsultaController {

    private final MesaRepository mesaRepository;
    private final PedidoConsultaService pedidoConsultaService;

    public PedidoConsultaController(
            MesaRepository mesaRepository,
            PedidoConsultaService pedidoConsultaService) {

        this.mesaRepository = mesaRepository;
        this.pedidoConsultaService = pedidoConsultaService;
    }

    @GetMapping("/{numeroMesa}")
    public ResponseEntity<Map<String, Object>> consultarPedidos(
            @PathVariable Integer numeroMesa,
            @RequestHeader(
                    value = "X-Mesa-Token",
                    required = false
            ) String tokenMesa) {

        // 1. Buscar la mesa
        Mesa mesa = mesaRepository
                .findByNumeroMesa(numeroMesa)
                .orElse(null);

        if (mesa == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "ok", false,
                            "message", "Mesa no encontrada"
                    ));
        }

        // 2. Validar que mesa y QR esten activos
        if (!Boolean.TRUE.equals(mesa.getActivo())
                || !Boolean.TRUE.equals(mesa.getQrActivo())) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                            "ok", false,
                            "message", "Mesa o QR inactivo"
                    ));
        }

        // 3. Validar token QR
        if (!tokenQrValido(mesa, tokenMesa)) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                            "ok", false,
                            "message", "Token QR invalido"
                    ));
        }

        // 4. Consultar los pedidos mediante el servicio
        return ResponseEntity.ok(
                Map.of(
                        "ok", true,
                        "data",
                        pedidoConsultaService.obtenerPedidosDeMesa(
                                mesa.getIdMesa()
                        )
                )
        );
    }
    
private boolean tokenQrValido(Mesa mesa, String tokenMesa) {

    if (tokenMesa == null
            || tokenMesa.isBlank()
            || mesa.getQrToken() == null) {

        return false;
    }

    return MessageDigest.isEqual(
            mesa.getQrToken().getBytes(StandardCharsets.UTF_8),
            tokenMesa.getBytes(StandardCharsets.UTF_8)
    );
}

}
