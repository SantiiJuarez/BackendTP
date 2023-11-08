package com.tpi.TP.Repositories;

import com.tpi.TP.models.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {
}
