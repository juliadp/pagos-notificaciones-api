package com.pagos.notificaciones.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payment_event")
public class PaymentEvent {
    @Id
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "payment_id")
    private String paymentId;
    private String status;
    @Column(name = "occurred_at")
    private Instant occurredAt;
    @Column(name = "received_at")
    private Instant receivedAt;
    private String payload;

    public PaymentEvent() {}

    public PaymentEvent(String eventId, String paymentId, String status, Instant occurredAt, String payload) {
        this.eventId = eventId;
        this.paymentId = paymentId;
        this.status = status;
        this.occurredAt = occurredAt;
        this.receivedAt = Instant.now();
        this.payload = payload;
    }

    public String getEventId() { return eventId; }
    public String getPaymentId() { return paymentId; }
    public String getStatus() { return status; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getReceivedAt() { return receivedAt; }
    public String getPayload() { return payload; }
}
