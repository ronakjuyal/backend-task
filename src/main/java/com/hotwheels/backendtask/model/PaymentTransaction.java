package com.hotwheels.backendtask.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dealerId;
    private Double amount;
    private String method;  // UPI / Card / NetBanking
    private String status;  // PENDING / SUCCESS / FAILED
}
