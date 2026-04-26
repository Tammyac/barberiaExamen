package com.barberia.barberia.controller;

import com.barberia.barberia.model.Appointment;
import com.barberia.barberia.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String clienteEmail) {
        return ResponseEntity.ok(service.getAll(clienteEmail));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Appointment appointment) {
        try {
            Appointment nuevaCita = service.create(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Horario no disponible")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            service.cancelar(id);
            return ResponseEntity.ok("Cita cancelada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}