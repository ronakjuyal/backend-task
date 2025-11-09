package com.hotwheels.backendtask.repository;

import com.hotwheels.backendtask.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentTransaction, Long> {
}
