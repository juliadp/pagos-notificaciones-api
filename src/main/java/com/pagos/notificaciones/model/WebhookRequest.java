package com.pagos.notificaciones.model;

import java.math.BigDecimal;
import java.time.Instant;

// asumo este formato de payload de PayGate
public record WebhookRequest(
        String eventId,
        String paymentId,
        String status,
        BigDecimal amount,
        String currency,
        Instant timestamp) {
}
