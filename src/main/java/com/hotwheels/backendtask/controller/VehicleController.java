package com.hotwheels.backendtask.controller;

import com.hotwheels.backendtask.dto.ApiResponse;
import com.hotwheels.backendtask.dto.VehicleDto;
import com.hotwheels.backendtask.mapper.VehicleMapper;
import com.hotwheels.backendtask.model.Dealer;
import com.hotwheels.backendtask.model.Vehicle;
import com.hotwheels.backendtask.repository.DealerRepository;
import com.hotwheels.backendtask.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleController(VehicleRepository vehicleRepository,
                             DealerRepository dealerRepository,
                             VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.dealerRepository = dealerRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleDto>>> getAll() {
        var dtos = vehicleRepository.findAll().stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.<List<VehicleDto>>builder()
                .success(true)
                .data(dtos)
                .message("OK")
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleDto>> getById(@PathVariable Long id) {
        return vehicleRepository.findById(id)
                .map(v -> ResponseEntity.ok(ApiResponse.<VehicleDto>builder()
                        .success(true)
                        .data(vehicleMapper.toDto(v))
                        .message("OK")
                        .build()))
                .orElse(ResponseEntity.status(404).body(ApiResponse.<VehicleDto>builder()
                        .success(false)
                        .message("Vehicle not found")
                        .build()));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VehicleDto dto) {
        if (dto.getDealerId() == null) {
            return ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                    .success(false)
                    .message("dealerId required")
                    .build());
        }

        var optDealer = dealerRepository.findById(dto.getDealerId());
        if (optDealer.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                    .success(false)
                    .message("dealer not found")
                    .build());
        }

        Dealer dealer = optDealer.get();
        Vehicle vehicle = vehicleMapper.toEntity(dto);
        vehicle.setDealer(dealer);
        Vehicle saved = vehicleRepository.save(vehicle);

        return ResponseEntity.ok(ApiResponse.<VehicleDto>builder()
                .success(true)
                .data(vehicleMapper.toDto(saved))
                .message("Vehicle created")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleDto>> update(@PathVariable Long id, @Valid @RequestBody VehicleDto payload) {
        return vehicleRepository.findById(id).map(v -> {
            v.setModel(payload.getModel());
            v.setPrice(payload.getPrice());
            if (payload.getStatus() != null) {
                try {
                    v.setStatus(Vehicle.Status.valueOf(payload.getStatus()));
                } catch (IllegalArgumentException ignored) { }
            }
            if (payload.getDealerId() != null) {
                dealerRepository.findById(payload.getDealerId()).ifPresent(v::setDealer);
            }
            Vehicle saved = vehicleRepository.save(v);
            return ResponseEntity.ok(ApiResponse.<VehicleDto>builder()
                    .success(true)
                    .data(vehicleMapper.toDto(saved))
                    .message("Vehicle updated")
                    .build());
        }).orElse(ResponseEntity.status(404).body(ApiResponse.<VehicleDto>builder()
                .success(false)
                .message("Vehicle not found")
                .build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!vehicleRepository.existsById(id)) {
            return ResponseEntity.status(404).body(ApiResponse.<Void>builder()
                    .success(false)
                    .message("Vehicle not found")
                    .build());
        }
        vehicleRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Vehicle deleted")
                .build());
    }

    // Fetch vehicles belonging to PREMIUM dealers only
    @GetMapping("/premium-dealers")
    public ResponseEntity<ApiResponse<List<VehicleDto>>> vehiclesOfPremiumDealers() {
        var premiumDealers = dealerRepository.findBySubscriptionType(Dealer.SubscriptionType.PREMIUM);
        var dtos = vehicleRepository.findByDealerIn(premiumDealers).stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.<List<VehicleDto>>builder()
                .success(true)
                .data(dtos)
                .message("OK")
                .build());
    }
}
