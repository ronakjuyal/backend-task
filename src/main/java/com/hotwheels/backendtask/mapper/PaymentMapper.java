package com.hotwheels.backendtask.mapper;

import com.hotwheels.backendtask.dto.PaymentDto;
import com.hotwheels.backendtask.model.PaymentTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(PaymentTransaction tx);
    PaymentTransaction toEntity(PaymentDto dto);
}
