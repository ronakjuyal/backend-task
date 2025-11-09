package com.hotwheels.backendtask.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dealers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    public enum SubscriptionType {
        BASIC,
        PREMIUM
    }
}
