package com.hotwheels.backendtask.mapper;

import com.hotwheels.backendtask.dto.DealerDto;
import com.hotwheels.backendtask.model.Dealer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerMapper {
    DealerDto toDto(Dealer dealer);
    Dealer toEntity(DealerDto dto);
}
