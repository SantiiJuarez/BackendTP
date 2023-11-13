package com.tpi.TP.services;

import com.tpi.TP.Repositories.TarifaRepository;
import com.tpi.TP.models.Alquiler;
import com.tpi.TP.models.Tarifas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifasService {
    @Autowired
    private TarifaRepository tarifaRepository;

    public List<Tarifas> findAll() {
        List<Tarifas> tarifas = tarifaRepository.findAll();
        return tarifas;
    }

    public Tarifas findByDiaSemanaId(long diaSemana) {
        List<Tarifas> tarifas = tarifaRepository.findAll();
        Tarifas tarifaDiaSemana = null;
        for (Tarifas tarifa : tarifas) {
            if (tarifa.getDiaSemana() == diaSemana) {
                tarifaDiaSemana = tarifa;
                break;
            }
        }
        return tarifaDiaSemana;
    }

    public Tarifas findByDiscount(Integer anio, Integer mes, Integer dia){
        List<Tarifas> tarifas = tarifaRepository.findAll();
        Tarifas tarifaDescuento = null;
        for (Tarifas tarifa : tarifas) {
            Integer tarifaDiaMes = tarifa.getDiaMes();
            Integer tarifaMes = tarifa.getMes();
            Integer tarifaAnio = tarifa.getAnio();

            if (tarifaDiaMes != null && tarifaMes != null && tarifaAnio != null &&
                    tarifaDiaMes.equals(dia) && tarifaMes.equals(mes) && tarifaAnio.equals(anio)) {
                tarifaDescuento = tarifa;
                break;
            }
        }
        return tarifaDescuento;
    }
}
