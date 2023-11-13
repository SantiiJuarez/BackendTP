package com.tpi.TP.controllers;

import com.tpi.TP.DTOs.EstacionDTO;
import com.tpi.TP.services.EstacionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estacion")
public class EstacionController {
    @Autowired
    private EstacionService estacionService;

    @Operation(summary = "Devuelve todas las estaciones del sistema")
    @GetMapping("/allEstaciones")
    public ResponseEntity<List<EstacionDTO>> getAll(){
        List<EstacionDTO> estaciones = estacionService.findAll();
        return ResponseEntity.ok(estaciones);
    }

    @Operation(summary = "Devuelve la estación más cercana en base a una ubicación ingresada")
    @GetMapping("findEstacionByUbication")
    public ResponseEntity<EstacionDTO> findById(@RequestParam double latitud, double longitud){
        EstacionDTO estacion = estacionService.findEstacionMasCercana(latitud, longitud);
        return ResponseEntity.ok(estacion);
    }

    @Operation(summary = "Devuelve la estación correspondiente al Id ingresado")
    @GetMapping("findEstacionById/{id}")
    public ResponseEntity<EstacionDTO> findById(@PathVariable Long id){
        EstacionDTO estacion = estacionService.getById(id);
        return ResponseEntity.ok(estacion);
    }

    @Operation(summary = "Crea una estación nueva al sistema")
    @PostMapping("createEstacion")
    public ResponseEntity<EstacionDTO> create(@RequestBody EstacionDTO estacionDTO){
        EstacionDTO estacion = estacionService.create(estacionDTO);
        String response = "create Estacion";
        return ResponseEntity.ok(estacion);
    }

    @Operation(summary = "Actualiza la estación correspondiente al Id ingresado")
    @PutMapping("/updateEstacion/{id}")
    public ResponseEntity<EstacionDTO> updateEstacion(@PathVariable Long id, @RequestBody EstacionDTO estacionDTO) {
        EstacionDTO updatedEstacion = estacionService.update(id,estacionDTO);
        if (updatedEstacion != null) {
            return ResponseEntity.ok(updatedEstacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Elimina la estación correspondiente al Id ingresado")
    @DeleteMapping("/deleteEstacion/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String response = estacionService.delete(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Devuelve la distancia entre estaciones")
    @GetMapping("/getDistancia/{id1}/{id2}")
    public ResponseEntity<Double> calcularDistancia(@PathVariable Long id1,@PathVariable Long id2){
        Double distancia = estacionService.findDistanciaByEstaciones(id1, id2);
        return ResponseEntity.ok(distancia);
    }

}
