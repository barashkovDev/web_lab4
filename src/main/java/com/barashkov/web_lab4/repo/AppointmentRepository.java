package com.barashkov.web_lab4.repo;

import com.barashkov.web_lab4.models.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
