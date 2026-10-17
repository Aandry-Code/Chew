
package com.example.chew.repository;

import com.example.chew.entity.SeguimientoCocina;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeguimientoCocinaRepository
        extends JpaRepository<SeguimientoCocina, Integer> {

    Optional<SeguimientoCocina> findByIdDetalleProducto(
            Integer idDetalleProducto
    );

    @Query(value = """
        SELECT sc.*
        FROM public.seguimiento_cocina sc
        INNER JOIN public.detalle_producto dp
            ON sc.id_detalle_producto = dp.id_detalle_producto
        WHERE dp.id_pedido = :idPedido
        ORDER BY sc.id_seguimiento
        """, nativeQuery = true)
    List<SeguimientoCocina> buscarPorPedido(
            @Param("idPedido") Integer idPedido
    );
}
