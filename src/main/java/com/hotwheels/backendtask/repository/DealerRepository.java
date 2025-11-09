package com.hotwheels.backendtask.repository;

import com.hotwheels.backendtask.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findBySubscriptionType(Dealer.SubscriptionType subscriptionType);
}
