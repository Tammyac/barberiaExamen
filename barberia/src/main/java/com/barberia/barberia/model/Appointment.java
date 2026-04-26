package com.barberia.barberia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.barberia.barberia.model.Appointment;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;

    private LocalDateTime fechaHora;
    private Integer duracionMin;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus estado;

    private LocalDateTime creadoEn;

    public Appointment() {
    }

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = AppointmentStatus.RESERVADA;
        }

        if (this.duracionMin == null) {
            this.duracionMin = 30;
        }
    }

    public Long getId() {
        return id;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Integer getDuracionMin() {
        return duracionMin;
    }

    public AppointmentStatus getEstado() {
        return estado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setDuracionMin(Integer duracionMin) {
        this.duracionMin = duracionMin;
    }

    public void setEstado(AppointmentStatus estado) {
        this.estado = estado;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}