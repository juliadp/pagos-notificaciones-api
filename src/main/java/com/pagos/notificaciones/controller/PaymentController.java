package com.pagos.notificaciones.controller;

import com.pagos.notificaciones.model.Payment;
import com.pagos.notificaciones.model.PaymentEvent;
import com.pagos.notificaciones.service.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final WebhookService service;

    public PaymentController(WebhookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable String id) {
        return service.getPayment(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/events")
    public List<PaymentEvent> history(@PathVariable String id) {
        return service.getHistory(id);
    }
}
