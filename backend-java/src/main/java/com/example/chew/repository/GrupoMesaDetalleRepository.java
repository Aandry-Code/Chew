
package com.example.chew.repository;

import com.example.chew.entity.GrupoMesaDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoMesaDetalleRepository
        extends JpaRepository<GrupoMesaDetalle, Integer> {

    boolean existsByIdGrupoMesaAndIdMesa(
            Integer idGrupoMesa,
            Integer idMesa
    );

    List<GrupoMesaDetalle> findByIdGrupoMesa(
            Integer idGrupoMesa
    );
}


