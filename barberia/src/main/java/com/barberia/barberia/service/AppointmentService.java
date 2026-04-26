package com.barberia.barberia.service;

import com.barberia.barberia.model.Appointment;
import com.barberia.barberia.model.AppointmentStatus;
import com.barberia.barberia.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    // listar por email
    public List<Appointment> getAll(String clienteEmail) {
        if (clienteEmail != null && !clienteEmail.isEmpty()) {
            return repository.findByClienteEmail(clienteEmail);
        }
        return repository.findAll();
    }

    // Crear las citas con validaciones
    @Transactional
    public Appointment create(Appointment appointment) {

        //  validar fecha
        if (appointment.getFechaHora() == null ||
                appointment.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha debe ser futura");
        }

        // duración por defecto
        if (appointment.getDuracionMin() == null) {
            appointment.setDuracionMin(30);
        }

        // estado por defecto
        appointment.setEstado(AppointmentStatus.RESERVADA);

        LocalDateTime inicioNuevo = appointment.getFechaHora();
        LocalDateTime finNuevo = inicioNuevo.plusMinutes(appointment.getDuracionMin());

        // validar solapamiento
        List<Appointment> existentes = repository.findAll();

        for (Appointment a : existentes) {

            if (a.getEstado() == AppointmentStatus.CANCELADA) continue;

            LocalDateTime inicioExistente = a.getFechaHora();
            LocalDateTime finExistente = inicioExistente.plusMinutes(a.getDuracionMin());

            boolean solapa =
                    inicioNuevo.isBefore(finExistente) &&
                            inicioExistente.isBefore(finNuevo);

            if (solapa) {
                throw new RuntimeException("Horario no disponible)");
            }
        }

        return repository.save(appointment);
    }

    //Cancelar cita
    public void cancelar(Long id) {

        Appointment cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado(AppointmentStatus.CANCELADA);

        repository.save(cita);
    }
}
