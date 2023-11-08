package com.tpi.TP.controllers;

import com.tpi.TP.DTOs.AlquilerDTO;
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

    @PostMapping("create")
    public ResponseEntity<AlquilerDTO> create(@RequestBody AlquilerDTO alquilerDTO){
        AlquilerDTO alquiler = alquilerService.create(alquilerDTO);
        return ResponseEntity.ok(alquiler);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlquilerDTO> updateAlquiler(@PathVariable Long id, @RequestBody AlquilerDTO alquilerDTO) {
        AlquilerDTO updateAlquiler = alquilerService.update(id, alquilerDTO);
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
