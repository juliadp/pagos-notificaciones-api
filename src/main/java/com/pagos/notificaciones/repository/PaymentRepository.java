package com.pagos.notificaciones.repository;

import com.pagos.notificaciones.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
