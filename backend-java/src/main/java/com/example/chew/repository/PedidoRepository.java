
package com.example.chew.repository;

import com.example.chew.entity.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository
        extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByIdGrupoMesaOrderByFechaCreacionDesc(
            Integer idGrupoMesa
    );
}
