
package com.example.chew.service;

import com.example.chew.dto.PedidoRequestDTO;
import com.example.chew.dto.PedidoResponseDTO;

public interface PedidoService {

    PedidoResponseDTO crearPedidoMesa(
            PedidoRequestDTO solicitud
    );
}
