package com.tpi.TP.controllers;

import com.tpi.TP.DTOs.AlquilerCreadoDTO;
import com.tpi.TP.DTOs.AlquilerDTO;
import com.tpi.TP.DTOs.AlquilerFinalizadoDTO;
import com.tpi.TP.services.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/allAlquileres")
    public ResponseEntity<List<AlquilerDTO>> getAll(){
        List<AlquilerDTO> alquileres = alquilerService.findAll();
        return ResponseEntity.ok(alquileres);
    }

    @PostMapping("/createAlquiler")
    public ResponseEntity<AlquilerDTO> create(@RequestBody AlquilerCreadoDTO alquilerCreadoDTO){
        AlquilerDTO alquiler = alquilerService.createAlquilerPorEstacion(alquilerCreadoDTO);
        return ResponseEntity.ok(alquiler);
    }

    @GetMapping("/getAlquileresByClienteId/{id}")
    public ResponseEntity<List<AlquilerDTO>> getAlquileresByClienteId(@PathVariable Long id){
        List<AlquilerDTO> alquileres = alquilerService.findAlquileresById(id);
        return ResponseEntity.ok(alquileres);
    }

    @PutMapping("/updateAlquiler")
    public ResponseEntity<AlquilerDTO> updateAlquiler(@RequestBody AlquilerFinalizadoDTO alquilerFinalizadoDTO) {
        AlquilerDTO updateAlquiler = alquilerService.updateAlquiler(alquilerFinalizadoDTO);
        if (updateAlquiler != null){
            return ResponseEntity.ok(updateAlquiler);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        String responnse = alquilerService.delete(id);
        return ResponseEntity.ok(responnse);
    }
}
