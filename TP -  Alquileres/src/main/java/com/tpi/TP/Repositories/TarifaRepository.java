package com.tpi.TP.Repositories;

import com.tpi.TP.models.Tarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifas, Long> {

}
