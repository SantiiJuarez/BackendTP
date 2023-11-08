package com.tpi.TP.services;

import com.tpi.TP.Repositories.EstacionRepository;
import com.tpi.TP.models.Estacion;
import com.tpi.TP.DTOs.EstacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    public String delete(Long id) {
        estacionRepository.deleteById(id);
        return "Estacion deleted";
    }


    public List<EstacionDTO> findAll() {
        List<Estacion> estaciones = estacionRepository.findAll();
        return estaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private EstacionDTO convertToDto(Estacion estacion) {
        EstacionDTO estacionDTO = new EstacionDTO();
        estacionDTO.setEstacionId(estacion.getEstacionId());
        estacionDTO.setName(estacion.getNombreEstacion());
        estacionDTO.setFechaHoraCreacion(estacion.getFechaHoraCreacion());
        estacionDTO.setLatitud(estacion.getLatitud());
        estacionDTO.setLongitud(estacion.getLongitud());
        return estacionDTO;
    }

    public EstacionDTO create(EstacionDTO estacionDTO) {
        Estacion estacion = new Estacion();
        estacion.setNombreEstacion(estacionDTO.getName());
        estacion.setLongitud(estacionDTO.getLongitud());
        estacion.setLatitud(estacionDTO.getLatitud());
        estacion.setFechaHoraCreacion(estacionDTO.getFechaHoraCreacion());
        estacion = estacionRepository.save(estacion);
        estacionDTO.setEstacionId(estacion.getEstacionId());
        return estacionDTO;
    }

    public EstacionDTO update(Long id, EstacionDTO estacionDTO) {
        Estacion estacion = estacionRepository.findById(id).get();
        estacion.setNombreEstacion(estacionDTO.getName());
        estacion.setLongitud(estacionDTO.getLongitud());
        estacion.setLatitud(estacionDTO.getLatitud());
        estacion.setFechaHoraCreacion(estacionDTO.getFechaHoraCreacion());
        estacionRepository.save(estacion);
        estacionDTO.setEstacionId(estacion.getEstacionId());
       return estacionDTO;
    }

    public EstacionDTO getById(Long id) {
        Estacion estacion = estacionRepository.findById(id).get();
        EstacionDTO estacionDTO = new EstacionDTO();
        estacionDTO.setEstacionId(estacion.getEstacionId());
        estacionDTO.setName(estacion.getNombreEstacion());
        estacionDTO.setLatitud(estacion.getLatitud());
        estacionDTO.setLongitud(estacion.getLongitud());
        estacionDTO.setFechaHoraCreacion(estacion.getFechaHoraCreacion());
        return estacionDTO;
    }
}
