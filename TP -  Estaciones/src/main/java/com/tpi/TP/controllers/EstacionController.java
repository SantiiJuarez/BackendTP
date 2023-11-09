package com.tpi.TP.controllers;

import com.tpi.TP.DTOs.EstacionDTO;
import com.tpi.TP.DTOs.UbicacionDTO;
import com.tpi.TP.services.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estacion")
public class EstacionController {
    @Autowired
    private EstacionService estacionService;

    @GetMapping("/allEstaciones")
    public ResponseEntity<List<EstacionDTO>> getAll(){
        List<EstacionDTO> estaciones = estacionService.findAll();
        return ResponseEntity.ok(estaciones);
    }

    @GetMapping("findEstacionByUbication")
    public ResponseEntity<EstacionDTO> findById(@RequestBody UbicacionDTO ubicacionDTO){
        EstacionDTO estacion = estacionService.findEstacionMasCercana(ubicacionDTO);
        return ResponseEntity.ok(estacion);
    }

    @GetMapping("findEstacionById/{id}")
    public ResponseEntity<EstacionDTO> findById(@PathVariable Long id){
        EstacionDTO estacion = estacionService.getById(id);
        return ResponseEntity.ok(estacion);
    }

    @GetMapping("findEstacionByCiudad/{id}")
    public ResponseEntity<String> findByCiudad(@PathVariable Long id){
        String response = "find by Ciudad";
        return ResponseEntity.ok(response);
    }

    @GetMapping("findEstacionByUbicacion/{id}")
    public ResponseEntity<String> update(@PathVariable  Long id){
        String response = "find by Ubicacion";
        return ResponseEntity.ok(response);
    }

    @PostMapping("createEstacion")
    public ResponseEntity<EstacionDTO> create(@RequestBody EstacionDTO estacionDTO){
        EstacionDTO estacion = estacionService.create(estacionDTO);
        String response = "create Estacion";
        return ResponseEntity.ok(estacion);
    }

    @PutMapping("/updateEstacion/{id}")
    public ResponseEntity<EstacionDTO> updateEstacion(@PathVariable Long id, @RequestBody EstacionDTO estacionDTO) {
        EstacionDTO updatedEstacion = estacionService.update(id,estacionDTO);
        if (updatedEstacion != null) {
            return ResponseEntity.ok(updatedEstacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteEstacion/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String response = estacionService.delete(id);
        return ResponseEntity.ok(response);
    }

}
