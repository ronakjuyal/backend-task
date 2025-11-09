package com.hotwheels.backendtask.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;

    private String model;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        AVAILABLE,
        SOLD
    }
}
