package com.tpi.TP.services;

import com.tpi.TP.DTOs.AlquilerCreadoDTO;
import com.tpi.TP.DTOs.AlquilerDTO;
import com.tpi.TP.DTOs.AlquilerFinalizadoDTO;
import com.tpi.TP.DTOs.EstacionDTO;
import com.tpi.TP.Repositories.AlquilerRepository;
import com.tpi.TP.models.Alquiler;
import com.tpi.TP.models.Estacion;
import com.tpi.TP.models.Tarifas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlquilerService {
    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TarifasService tarifasService;

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

    public AlquilerDTO updateAlquiler(AlquilerFinalizadoDTO alquilerFinalizadoDTO){

        Alquiler alquiler = alquilerRepository.findById(alquilerFinalizadoDTO.getAlquilerId()).get();
        alquiler.setEstado(2);
        ResponseEntity<EstacionDTO> responseEntity = restTemplate.getForEntity("http://localhost:8084/api/estacion/findEstacionById/"+alquilerFinalizadoDTO.getEstacionDevolucionId(), EstacionDTO.class);
        EstacionDTO estacionDTODevolucion = responseEntity.getBody();
        Estacion estacion = convertDTOtoEstacion(estacionDTODevolucion);
        alquiler.setEstacionDevolucion(estacion);
        alquiler.setFechaHoraDevolucion(LocalDateTime.now());
        alquiler.setMonto(CalculateAmount(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion(), alquiler, alquilerFinalizadoDTO.getMoneda()));
        alquilerRepository.save(alquiler);

        AlquilerDTO alquilerDTO = convertToDto(alquiler);

        return alquilerDTO;
    }

    public double CalculateAmount(LocalDateTime fechaHoraRetiro, LocalDateTime fechaHoraDevolucion, Alquiler alquiler, String moneda){
        //Calcular el tiempo entre el retiro y la devolución y expresarlo en minutos
        Duration duracion = Duration.between(fechaHoraRetiro, fechaHoraDevolucion);
        long minutosDiferencia = duracion.toMinutes();
        double monto;


        //Obtener el día de la semana de la devolución para encontrar la tarifa determinada
        DayOfWeek diaDeLaSemana = fechaHoraDevolucion.getDayOfWeek();
        //Expresar el dia de la semana en numero
        int numeroDiaDeLaSemana = diaDeLaSemana.getValue();
        Tarifas tarifa = tarifasService.findByDiaSemanaId(numeroDiaDeLaSemana);
        alquiler.setTarifaId(tarifa.getId());
        double montoAdicionalxKm = CalculateAmountByKm(tarifa, alquiler);
        double descuento = calculateDiscount(fechaHoraDevolucion, minutosDiferencia, alquiler);
        // Verificar si la diferencia es mayor a 31 minutos
        if (minutosDiferencia >= 31) {
            monto = (tarifa.getMontoFijoAlquiler() + (((double) minutosDiferencia / 60) * tarifa.getMontoHora()) + montoAdicionalxKm) - descuento;
        } else {
            monto = (tarifa.getMontoFijoAlquiler() + minutosDiferencia * tarifa.getMontoMinutoFraccion() + montoAdicionalxKm) - descuento;
        }
        if(moneda != null){
            monto = convertMount(moneda, monto);
        }
        return monto;
    }

    private Double convertMount(String moneda, double monto){
        // Configurar el encabezado y el cuerpo de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear el cuerpo de la solicitud
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("moneda_destino", moneda);
        requestBody.put("importe", monto);

        // Crear la entidad de solicitud con encabezados y cuerpo
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Enviar la solicitud POST y recibir la respuesta
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                "http://34.82.105.125:8080/convertir",
                requestEntity,
                Map.class
        );

        // Obtener el importe de la respuesta
        return (Double) responseEntity.getBody().get("importe");
    }
    private Double calculateDiscount(LocalDateTime fechaHoraDevolucion, long minutosDiferencia, Alquiler alquiler){
        LocalDate fecha = fechaHoraDevolucion.toLocalDate();
        Integer dia = fecha.getDayOfMonth();
        Integer mes = fecha.getMonthValue();
        Integer anio = fecha.getYear();
        Double descuento = 0.0;
        Tarifas tarifaDescuento = tarifasService.findByDiscount(anio, mes ,dia);

        if(tarifaDescuento != null){
            double montoAdicionalxKm = CalculateAmountByKm(tarifaDescuento, alquiler);
            if (minutosDiferencia >= 31) {
                descuento = tarifaDescuento.getMontoFijoAlquiler() + (((double) minutosDiferencia / 60) * tarifaDescuento.getMontoHora()) + montoAdicionalxKm;
            } else {
                descuento = tarifaDescuento.getMontoFijoAlquiler() + minutosDiferencia * tarifaDescuento.getMontoMinutoFraccion() + montoAdicionalxKm;
            }
        }
        return descuento;
    }

    private Double CalculateAmountByKm(Tarifas tarifa, Alquiler alquiler) {
        ResponseEntity<Double> responseEntity = restTemplate.getForEntity(
                "http://localhost:8084/api/estacion/getDistancia/" + alquiler.getEstacionRetiro().getEstacionId() +
                        "/" + alquiler.getEstacionDevolucion().getEstacionId(), Double.class);
        Double montoAdicional = responseEntity.getBody() * tarifa.getMontoKm();
        return montoAdicional;
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
