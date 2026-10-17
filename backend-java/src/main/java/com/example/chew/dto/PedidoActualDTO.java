
package com.example.chew.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoActualDTO(

        Integer id_pedido,
        String codigo,
        String estado_db,
        String fecha,
        String hora,
        BigDecimal total,
        List<Producto> productos

) {

    public record Producto(

            String nombre,
            Integer cantidad,
            BigDecimal precio,
            BigDecimal subtotal,
            String observacion,
            String estadoCocina

    ) {}

}
