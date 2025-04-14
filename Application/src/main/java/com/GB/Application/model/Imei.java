package com.GB.Application.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "imei_numbers")
public class Imei {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String imei;

    private boolean registered = false;
}