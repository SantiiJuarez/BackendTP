package com.tpi.TP.services;

import com.tpi.TP.DTOs.AlquilerCreadoDTO;
import com.tpi.TP.DTOs.AlquilerDTO;
import com.tpi.TP.DTOs.EstacionDTO;
import com.tpi.TP.Repositories.AlquilerRepository;
import com.tpi.TP.models.Alquiler;
import com.tpi.TP.models.Estacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlquilerService {
    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    public AlquilerDTO createAlquilerPorEstacion(AlquilerCreadoDTO alquilerCreadoDTO){
        Alquiler alquiler = new Alquiler();
        alquiler.setClienteId(alquilerCreadoDTO.getClienteId());
        alquiler.setEstado(1);
        ResponseEntity<EstacionDTO> responseEntity = restTemplate.getForEntity("http://localhost:8084/api/estacion/findEstacionById/"+alquilerCreadoDTO.getEstacionRetiro(), EstacionDTO.class);
        EstacionDTO estacionDTORetiro = responseEntity.getBody();
        Estacion estacion = convertDTOtoEstacion(estacionDTORetiro);
        alquiler.setEstacionRetiro(estacion);
        alquiler.setFechaHoraRetiro(LocalDateTime.now());
        alquilerRepository.save(alquiler);
        AlquilerDTO alquilerDTO = convertToDto(alquiler);
        return alquilerDTO;
    }

    public Estacion convertDTOtoEstacion(EstacionDTO estacionDTO){
        Estacion estacion = new Estacion();
        estacion.setEstacionId(estacionDTO.getEstacionId());
        estacion.setNombreEstacion(estacionDTO.getName());
        estacion.setLongitud(estacionDTO.getLongitud());
        estacion.setLatitud(estacionDTO.getLatitud());
        return estacion;
    }

    /*public List<AlquilerDTO> findAll() {
        List<Alquiler> alquileres = alquilerRepository.findAll();
        return alquileres.stream().map(this::convertToDto).collect(Collectors.toList());
    }*/

    // PUNTO 6 - Obtener un listado de los alquileres realizados aplicando, por lo menos, un filtro

    public List<AlquilerDTO> findAlquileresById (long id){
        List<Alquiler> alquileres = alquilerRepository.findAll();
        List <Alquiler> listaAlquileresFiltro = new ArrayList<>();
        alquileres.forEach(alquiler -> {
            if (alquiler.getClienteId() == id) {
                listaAlquileresFiltro.add(alquiler);
            }
        });
        return listaAlquileresFiltro.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
