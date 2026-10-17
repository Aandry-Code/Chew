
package com.example.chew.repository;

import com.example.chew.entity.Mesa;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MesaRepository
        extends JpaRepository<Mesa, Integer> {

    Optional<Mesa> findByNumeroMesa(Integer numeroMesa);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT m FROM Mesa m
        WHERE m.numeroMesa = :numeroMesa
        """)
    Optional<Mesa> bloquearPorNumeroMesa(
            @Param("numeroMesa") Integer numeroMesa
    );
}


