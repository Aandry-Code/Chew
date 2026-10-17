
package com.example.chew.repository;
import java.util.List;
import com.example.chew.entity.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<Plato, Integer> {

    // Verificar si existe un codigo al registrar un plato
    boolean existsByCodigoPlato(String codigoPlato);

    // Verificar si otro plato utiliza ese codigo al actualizar
    boolean existsByCodigoPlatoAndIdPlatoNot(
            String codigoPlato,
            Integer idPlato
    );
    
    // Consultar solamente los platos activos
    List<Plato> findByActivoTrue();

}
