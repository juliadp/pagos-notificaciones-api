-- pagos y eventos recibidos de PayGate
CREATE TABLE payment (
  id            VARCHAR(64) PRIMARY KEY,
  status        VARCHAR(20) NOT NULL,
  amount        NUMERIC(14,2),
  currency      VARCHAR(3),
  last_event_at TIMESTAMP NOT NULL,
  updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE payment_event (
  event_id    VARCHAR(64) PRIMARY KEY,   -- idempotencia: el mismo evento no entra dos veces
  payment_id  VARCHAR(64) NOT NULL REFERENCES payment(id),
  status      VARCHAR(20) NOT NULL,
  occurred_at TIMESTAMP NOT NULL,
  received_at TIMESTAMP NOT NULL DEFAULT now(),
  payload     TEXT
);

CREATE INDEX idx_payment_event_payment ON payment_event(payment_id, occurred_at);
