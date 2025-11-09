package com.hotwheels.backendtask.repository;

import com.hotwheels.backendtask.model.Vehicle;
import com.hotwheels.backendtask.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByDealerId(Long dealerId);
    List<Vehicle> findByDealerIn(List<Dealer> dealers);
}
