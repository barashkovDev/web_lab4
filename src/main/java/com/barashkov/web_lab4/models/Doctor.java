package com.barashkov.web_lab4.models;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    public Doctor() {}

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String name;

    public String specialization;

}
