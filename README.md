# pagos-notificaciones-api

Servicio en Java (Spring Boot) que recibe los webhooks de PayGate y guarda el estado de cada pago.

## Que hace

- `POST /webhooks/paygate` recibe un evento de pago. Si el `eventId` ya se habia recibido no lo procesa de nuevo (idempotencia) y devuelve 200 igual para que PayGate no reintente.
- `GET /payments/{id}` devuelve el estado actual del pago.
- `GET /payments/{id}/events` devuelve el historial de eventos ordenado por fecha.

Si llega un evento con timestamp anterior al ultimo aplicado, se guarda en el historial pero no cambia el estado.

## Supuestos

- Cada evento trae un id unico (`eventId`).
- El estado del pago es el del evento mas reciente.

## Base de datos

El esquema esta en `src/main/resources/db/schema.sql` (PostgreSQL). Se crea al levantar la app.

## Como correrlo

```
docker run -d -p 5432:5432 -e POSTGRES_USER=pagos -e POSTGRES_PASSWORD=pagos -e POSTGRES_DB=pagos postgres:16
mvn spring-boot:run
```

## Tests

```
mvn test
```

Los tests usan H2 en memoria y prueban duplicados y eventos desordenados.
