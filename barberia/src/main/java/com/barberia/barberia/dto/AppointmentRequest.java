package com.barberia.barberia.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public record AppointmentRequest(
        String clienteNombre,
        String clienteEmail,
        String clienteTelefono,
        LocalDateTime fechaHora,
        Integer duracionMin // Usamos Integer para que pueda ser null
) {}