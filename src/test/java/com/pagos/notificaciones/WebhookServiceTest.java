package com.pagos.notificaciones;

import com.pagos.notificaciones.model.Payment;
import com.pagos.notificaciones.model.WebhookRequest;
import com.pagos.notificaciones.service.WebhookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebhookServiceTest {

    @Autowired
    WebhookService service;

    @Test
    void eventoDuplicadoNoSeProcesaDosVeces() {
        WebhookRequest req = new WebhookRequest("ev-1", "pay-1", "PENDING",
                new BigDecimal("100.00"), "ARS", Instant.parse("2024-01-01T10:00:00Z"));

        assertTrue(service.process(req));
        assertFalse(service.process(req)); // reintento de PayGate

        assertEquals(1, service.getHistory("pay-1").size());
    }

    @Test
    void eventoViejoNoPisaElEstado() {
        service.process(new WebhookRequest("ev-2", "pay-2", "CAPTURED",
                new BigDecimal("50.00"), "ARS", Instant.parse("2024-01-01T10:05:00Z")));
        service.process(new WebhookRequest("ev-3", "pay-2", "PENDING",
                new BigDecimal("50.00"), "ARS", Instant.parse("2024-01-01T10:00:00Z")));

        Payment p = service.getPayment("pay-2").orElseThrow();
        assertEquals("CAPTURED", p.getStatus());
        assertEquals(2, service.getHistory("pay-2").size());
    }
}
