package com.barashkov.web_lab4.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    public Appointment(){}

    public Appointment(Long userId, Long doctorId, LocalDateTime date) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Long userId;

    public Long doctorId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime date;

}
