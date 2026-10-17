
package com.example.chew.repository;

import com.example.chew.entity.DetalleProducto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleProductoRepository
        extends JpaRepository<DetalleProducto, Integer> {

    List<DetalleProducto> findByIdPedidoOrderByIdDetalleProductoAsc(
            Integer idPedido
    );
}
