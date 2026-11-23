package com.pagos.notificaciones.repository;

import com.pagos.notificaciones.model.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, String> {
    List<PaymentEvent> findByPaymentIdOrderByOccurredAtAsc(String paymentId);
}
