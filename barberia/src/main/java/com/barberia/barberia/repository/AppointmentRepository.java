package com.barberia.barberia.repository;
import com.barberia.barberia.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByClienteEmail(String clienteEmail);
}
