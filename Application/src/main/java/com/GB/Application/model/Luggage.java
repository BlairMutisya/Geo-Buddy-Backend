package com.GB.Application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "luggage")
public class Luggage extends Tracker {
    private String luggageType;
    private String color;
    private String description;
    private String trackerName;
}