package com.tpi.TP.services;

import com.tpi.TP.DTOs.AlquilerDTO;
import com.tpi.TP.Repositories.AlquilerRepository;
import com.tpi.TP.models.Alquiler;
import com.tpi.TP.models.Estacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlquilerService {
    @Autowired
    private AlquilerRepository alquilerRepository;

    private AlquilerDTO convertToDto(Alquiler alquiler){
        AlquilerDTO alquilerDTO = new AlquilerDTO();
        alquilerDTO.setAlquilerId(alquiler.getAlquilerId());
        alquilerDTO.setClienteId(alquiler.getClienteId());
        alquilerDTO.setEstado(alquiler.getEstado());
        alquilerDTO.setEstacionRetiro(alquiler.getEstacionRetiro());
        alquilerDTO.setEstacionDevolucion(alquiler.getEstacionDevolucion());
        alquilerDTO.setFechaHoraRetiro(alquiler.getFechaHoraRetiro());
        alquilerDTO.setFechaHoraDevolucion(alquiler.getFechaHoraDevolucion());
        alquilerDTO.setMonto(alquiler.getMonto());
        alquilerDTO.setTarifaId(alquiler.getTarifaId());
        return alquilerDTO;
    }

    public List<AlquilerDTO> findAll() {
        List<Alquiler> alquileres = alquilerRepository.findAll();
        return alquileres.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public AlquilerDTO create(AlquilerDTO alquilerDTO){
        Alquiler alquiler = new Alquiler();
        alquiler.setClienteId(alquilerDTO.getClienteId());
        alquiler.setEstado(alquilerDTO.getEstado());
        alquiler.setEstacionRetiro(alquilerDTO.getEstacionRetiro());
        alquiler.setEstacionDevolucion(alquilerDTO.getEstacionDevolucion());
        alquiler.setFechaHoraRetiro(alquilerDTO.getFechaHoraRetiro());
        alquiler.setFechaHoraDevolucion(alquilerDTO.getFechaHoraDevolucion());
        alquiler.setMonto(alquilerDTO.getMonto());
        alquiler.setTarifaId(alquilerDTO.getTarifaId());
        alquiler = alquilerRepository.save(alquiler);
        alquilerDTO.setAlquilerId(alquiler.getAlquilerId());
        return alquilerDTO;
    }

    public AlquilerDTO update(Long id, AlquilerDTO alquilerDTO){
        Alquiler alquiler = alquilerRepository.findById(id).get();
        alquiler.setClienteId(alquilerDTO.getClienteId());
        alquiler.setEstado(alquilerDTO.getEstado());
        alquiler.setEstacionRetiro(alquilerDTO.getEstacionRetiro());
        alquiler.setEstacionDevolucion(alquilerDTO.getEstacionDevolucion());
        alquiler.setFechaHoraRetiro(alquilerDTO.getFechaHoraRetiro());
        alquiler.setFechaHoraDevolucion(alquilerDTO.getFechaHoraDevolucion());
        alquiler.setMonto(alquilerDTO.getMonto());
        alquiler.setTarifaId(alquilerDTO.getTarifaId());
        alquiler = alquilerRepository.save(alquiler);
        alquilerDTO.setAlquilerId(alquiler.getAlquilerId());
        return alquilerDTO;
    }

    public String delete(Long id){
        alquilerRepository.deleteById(id);
        return "Alquiler deleted";
    }


    //PUNTO 3 - Iniciar el alquiler de una bicicleta desde una estación dada

    public AlquilerDTO createAlquilerPorEstacion(AlquilerDTO alquilerDTO, Long idEstacion){
        Alquiler alquiler = new Alquiler();
        alquiler.setClienteId(alquilerDTO.getClienteId());
        alquiler.setEstado(alquilerDTO.getEstado());

        /*alquiler.setEstacionRetiro(alquilerDTO.getEstacionRetiro());*/

        alquiler.setEstacionDevolucion(alquilerDTO.getEstacionDevolucion());
        alquiler.setFechaHoraRetiro(alquilerDTO.getFechaHoraRetiro());
        alquiler.setFechaHoraDevolucion(alquilerDTO.getFechaHoraDevolucion());
        alquiler.setMonto(alquilerDTO.getMonto());
        alquiler.setTarifaId(alquilerDTO.getTarifaId());
        alquiler = alquilerRepository.save(alquiler);
        alquilerDTO.setAlquilerId(alquiler.getAlquilerId());
        return alquilerDTO;
    }

}
