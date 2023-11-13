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

    public EstacionDTO findEstacionMasCercana(double latitud, double longitud) {
        List<Estacion> estaciones = estacionRepository.findAll();
        Estacion estacionMasCercana = null;
        double distanciaMasCorta = (100.00 * 110000)/1000; //Se establece una distancia genérica muy grande para comparar
        //110000 son los MetrosxGrado
        //Se divide entre 1000 para expresar la distancia en Km

        for (Estacion estacion : estaciones) {
            double estacionLatitud = estacion.getLatitud();
            double estacionLongitud = estacion.getLongitud();

            double distancia = (calcularDistancia(latitud, longitud, estacionLatitud, estacionLongitud) * 110000) / 1000;

            if (distancia < distanciaMasCorta) {
                distanciaMasCorta = distancia;
                estacionMasCercana = estacion;
            }
        }

        return convertToDto(estacionMasCercana);
    }


    public Double findDistanciaByEstaciones(long estacion1, long estacion2){

        Estacion estacionRetiro = estacionRepository.findById(estacion1).get();
        Estacion estacionDevolucion = estacionRepository.findById(estacion2).get();
        double estacionLatitudRetiro = estacionRetiro.getLatitud();

        double estacionLongitudRetiro = estacionRetiro.getLongitud();

        double estacionLatitudDevolucion = estacionDevolucion.getLatitud();
        double estacionLongitudDevolucion = estacionDevolucion.getLongitud();

        double distancia = (calcularDistancia(estacionLatitudRetiro, estacionLongitudRetiro, estacionLatitudDevolucion, estacionLongitudDevolucion) * 110000) / 1000;
        return distancia;
    }

    // Calcular la distancia entre dos puntos geográficos
    // d = √((x2 - x1)² + (y2 - y1)²
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {

        double diferenciaLatitud = lat1 - lat2;
        double diferenciaLongitud = lon2 - lon1;

        // Aplicar el teorema de Pitágoras
        double distancia = Math.sqrt(Math.pow(diferenciaLatitud,2)+ Math.pow(diferenciaLongitud,2));

        return distancia;
    }
}
