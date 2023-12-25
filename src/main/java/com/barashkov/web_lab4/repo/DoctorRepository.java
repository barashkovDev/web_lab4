package com.barashkov.web_lab4.repo;

import com.barashkov.web_lab4.models.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
