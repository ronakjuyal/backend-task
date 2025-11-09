package com.hotwheels.backendtask.controller;

import com.hotwheels.backendtask.dto.ApiResponse;
import com.hotwheels.backendtask.dto.DealerDto;
import com.hotwheels.backendtask.mapper.DealerMapper;
import com.hotwheels.backendtask.model.Dealer;
import com.hotwheels.backendtask.repository.DealerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    private final DealerRepository dealerRepository;
    private final DealerMapper dealerMapper;

    public DealerController(DealerRepository dealerRepository, DealerMapper dealerMapper) {
        this.dealerRepository = dealerRepository;
        this.dealerMapper = dealerMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DealerDto>>> getAll() {
        var dtos = dealerRepository.findAll()
                .stream()
                .map(dealerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.<List<DealerDto>>builder()
                .success(true)
                .data(dtos)
                .message("OK")
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DealerDto>> getById(@PathVariable Long id) {
        return dealerRepository.findById(id)
                .map(d -> ResponseEntity.ok(ApiResponse.<DealerDto>builder()
                        .success(true)
                        .data(dealerMapper.toDto(d))
                        .message("OK")
                        .build()))
                .orElse(ResponseEntity.status(404).body(ApiResponse.<DealerDto>builder()
                        .success(false)
                        .message("Dealer not found")
                        .build()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DealerDto>> create(@Valid @RequestBody DealerDto dto) {
        Dealer entity = dealerMapper.toEntity(dto);
        Dealer saved = dealerRepository.save(entity);
        return ResponseEntity.ok(ApiResponse.<DealerDto>builder()
                .success(true)
                .data(dealerMapper.toDto(saved))
                .message("Dealer created")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DealerDto>> update(@PathVariable Long id, @Valid @RequestBody DealerDto payload) {
        return dealerRepository.findById(id).map(d -> {
            d.setName(payload.getName());
            d.setEmail(payload.getEmail());
            if (payload.getSubscriptionType() != null) {
                try {
                    d.setSubscriptionType(Dealer.SubscriptionType.valueOf(payload.getSubscriptionType()));
                } catch (IllegalArgumentException ignored) { }
            }
            Dealer saved = dealerRepository.save(d);
            return ResponseEntity.ok(ApiResponse.<DealerDto>builder()
                    .success(true)
                    .data(dealerMapper.toDto(saved))
                    .message("Dealer updated")
                    .build());
        }).orElse(ResponseEntity.status(404).body(ApiResponse.<DealerDto>builder()
                .success(false)
                .message("Dealer not found")
                .build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!dealerRepository.existsById(id)) {
            return ResponseEntity.status(404).body(ApiResponse.<Void>builder()
                    .success(false)
                    .message("Dealer not found")
                    .build());
        }
        dealerRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Dealer deleted")
                .build());
    }
}
