package com.hotwheels.backendtask.mapper;

import com.hotwheels.backendtask.dto.VehicleDto;
import com.hotwheels.backendtask.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "dealer.id", target = "dealerId")
    VehicleDto toDto(Vehicle vehicle);

    @Mapping(target = "dealer", ignore = true)
    Vehicle toEntity(VehicleDto dto);
}
