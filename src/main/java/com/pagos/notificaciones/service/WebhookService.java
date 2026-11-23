package com.pagos.notificaciones.service;

import com.pagos.notificaciones.model.Payment;
import com.pagos.notificaciones.model.PaymentEvent;
import com.pagos.notificaciones.model.WebhookRequest;
import com.pagos.notificaciones.repository.PaymentEventRepository;
import com.pagos.notificaciones.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class WebhookService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventRepository eventRepository;

    public WebhookService(PaymentRepository paymentRepository, PaymentEventRepository eventRepository) {
        this.paymentRepository = paymentRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Procesa un webhook. Devuelve false si el evento ya se habia recibido (duplicado).
     */
    @Transactional
    public boolean process(WebhookRequest req) {
        // idempotencia por eventId
        if (eventRepository.existsById(req.eventId())) {
            return false;
        }

        eventRepository.save(new PaymentEvent(req.eventId(), req.paymentId(), req.status(),
                req.timestamp(), req.toString()));

        Payment payment = paymentRepository.findById(req.paymentId()).orElse(new Payment(req.paymentId()));

        // si el evento es mas viejo que el ultimo aplicado, solo queda en el historial
        // TODO: ver si hay estados que no deberian poder volver atras
        if (payment.getLastEventAt() == null || req.timestamp().isAfter(payment.getLastEventAt())) {
            payment.setStatus(req.status());
            payment.setLastEventAt(req.timestamp());
            if (req.amount() != null) {
                payment.setAmount(req.amount());
                payment.setCurrency(req.currency());
            }
        }
        payment.setUpdatedAt(Instant.now());
        paymentRepository.save(payment);
        return true;
    }

    public Optional<Payment> getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<PaymentEvent> getHistory(String paymentId) {
        return eventRepository.findByPaymentIdOrderByOccurredAtAsc(paymentId);
    }
}
