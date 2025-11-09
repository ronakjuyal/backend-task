package com.hotwheels.backendtask.controller;

import com.hotwheels.backendtask.dto.ApiResponse;
import com.hotwheels.backendtask.dto.PaymentDto;
import com.hotwheels.backendtask.mapper.PaymentMapper;
import com.hotwheels.backendtask.model.PaymentTransaction;
import com.hotwheels.backendtask.repository.PaymentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment API", description = "Simulate dealer subscription payment processing.")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public PaymentController(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Operation(
        summary = "Initiate payment",
        description = "Creates a payment with status PENDING, then auto-updates to SUCCESS after 5 seconds.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Payment initiated successfully",
                content = @Content(schema = @Schema(implementation = com.hotwheels.backendtask.dto.ApiResponse.class))
            )
        }
    )
    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse<PaymentDto>> initiate(@Valid @RequestBody PaymentDto req) {
        PaymentTransaction p = paymentMapper.toEntity(req);
        p.setStatus("PENDING");
        PaymentTransaction saved = paymentRepository.save(p);

        Long paymentId = saved.getId();

        scheduler.schedule(() -> {
            paymentRepository.findById(paymentId).ifPresent(tx -> {
                tx.setStatus("SUCCESS");
                paymentRepository.save(tx);
            });
        }, 5, TimeUnit.SECONDS);

        return ResponseEntity.ok(ApiResponse.<PaymentDto>builder()
                .success(true)
                .data(paymentMapper.toDto(saved))
                .message("Payment initiated")
                .build());
    }

    @Operation(
        summary = "Get payment by ID",
        description = "Fetch a specific payment record by ID.",
        parameters = {
            @Parameter(name = "id", description = "Payment ID", required = true)
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Payment record found",
                content = @Content(schema = @Schema(implementation = com.hotwheels.backendtask.dto.ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Payment not found",
                content = @Content(schema = @Schema(implementation = com.hotwheels.backendtask.dto.ApiResponse.class))
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentDto>> getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(payment -> ResponseEntity.ok(ApiResponse.<PaymentDto>builder()
                        .success(true)
                        .data(paymentMapper.toDto(payment))
                        .message("Payment fetched successfully")
                        .build()))
                .orElse(ResponseEntity.status(404).body(ApiResponse.<PaymentDto>builder()
                        .success(false)
                        .message("Payment not found")
                        .build()));
    }
}
