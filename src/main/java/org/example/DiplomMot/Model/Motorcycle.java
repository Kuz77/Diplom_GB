package org.example.DiplomMot.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "motorcycles")
public class Motorcycle {
    @Id
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String image;

    public Motorcycle() {
    }
}
