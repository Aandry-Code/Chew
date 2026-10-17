
package com.example.chew.repository;

import com.example.chew.entity.GrupoMesa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoMesaRepository
        extends JpaRepository<GrupoMesa, Integer> {

    @Query(value = """
        SELECT gm.*
        FROM public.grupos_mesa gm
        INNER JOIN public.grupo_mesa_detalle gmd
            ON gm.id_grupo_mesa = gmd.id_grupo_mesa
        WHERE gmd.id_mesa = :idMesa
          AND gm.estado = 'activo'
        ORDER BY gm.id_grupo_mesa
        """, nativeQuery = true)
    List<GrupoMesa> buscarGruposActivosDeMesa(
            @Param("idMesa") Integer idMesa
    );
}
