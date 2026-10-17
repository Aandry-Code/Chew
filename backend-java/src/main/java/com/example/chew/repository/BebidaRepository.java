
package com.example.chew.repository;

import com.example.chew.entity.Bebida;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BebidaRepository
        extends JpaRepository<Bebida, Integer> {

    // Verificar si existe un codigo de bebida
    boolean existsByCodigoBebida(String codigoBebida);

    // Verificar si otro registro utiliza ese codigo
    boolean existsByCodigoBebidaAndIdBebidaNot(
            String codigoBebida,
            Integer idBebida
    );

    // Consultar solamente las bebidas activas
    List<Bebida> findByActivoTrue();
}
