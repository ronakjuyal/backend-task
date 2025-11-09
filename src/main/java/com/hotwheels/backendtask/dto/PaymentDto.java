package com.hotwheels.backendtask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;

    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private Double amount;

    @NotBlank(message = "Payment method is required")
    private String method; // UPI / Card / NetBanking

    private String status; // PENDING / SUCCESS / FAILED
}
