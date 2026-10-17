
package com.example.chew.dto;

import java.math.BigDecimal;

public record PedidoResponseDTO(

    Integer idPedido,

    Integer numeroMesa,

    Integer idGrupoMesa,

    String codigo,

    String estadoPedido,

    BigDecimal total

) {}
