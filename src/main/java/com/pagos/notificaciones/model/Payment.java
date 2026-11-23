package com.pagos.notificaciones.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    private String id;
    private String status;
    private BigDecimal amount;
    private String currency;
    @Column(name = "last_event_at")
    private Instant lastEventAt;
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Payment() {}

    public Payment(String id) { this.id = id; }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Instant getLastEventAt() { return lastEventAt; }
    public void setLastEventAt(Instant lastEventAt) { this.lastEventAt = lastEventAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
