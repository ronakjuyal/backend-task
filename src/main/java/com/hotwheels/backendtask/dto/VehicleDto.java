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
public class VehicleDto {

    private Long id;

    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Status is required")
    private String status; // AVAILABLE or SOLD
}
