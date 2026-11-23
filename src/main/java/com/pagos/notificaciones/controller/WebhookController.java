package com.pagos.notificaciones.controller;

import com.pagos.notificaciones.model.WebhookRequest;
import com.pagos.notificaciones.service.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/paygate")
public class WebhookController {

    private final WebhookService service;

    public WebhookController(WebhookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> receive(@RequestBody WebhookRequest request) {
        if (request.eventId() == null || request.paymentId() == null || request.timestamp() == null) {
            return ResponseEntity.badRequest().body("faltan campos");
        }
        boolean processed = service.process(request);
        // devolvemos 200 igual si es duplicado para que PayGate no siga reintentando
        return ResponseEntity.ok(processed ? "procesado" : "duplicado");
    }
}
