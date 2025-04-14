package com.GB.Application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pets")
public class Pet extends Tracker {
    private String name;
    private String breed;
    private int age;
    private String description;
    private String trackerName;
}