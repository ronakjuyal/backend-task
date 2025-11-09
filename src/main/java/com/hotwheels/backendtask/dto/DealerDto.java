package com.hotwheels.backendtask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealerDto {

    private Long id;

    @NotBlank(message = "Dealer name is required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Subscription type is required")
    private String subscriptionType; // BASIC or PREMIUM
}
