# 🛍️ Shoe Store – Reactive DDD Hexagonal CRUD

Proyecto de ejemplo de una **tienda online de venta de zapatos**, desarrollado con:

- ✅ Java 17
- ✅ Spring Boot 3
- ✅ Spring WebFlux (reactivo)
- ✅ Arquitectura Hexagonal (Ports & Adapters)
- ✅ DDD (Domain-Driven Design)
- ✅ Persistencia intercambiable (R2DBC PostgreSQL o In-Memory)
- ✅ Flyway para migraciones
- ✅ Validaciones en dominio

---

## 🏗️ Arquitectura

El proyecto sigue **Arquitectura Hexagonal + DDD**.

                ┌─────────────────────────┐
                │       INBOUND           │
                │   (WebFlux Handlers)    │
                └────────────┬────────────┘
                             │
                    Application Layer
                  (UseCases / Services)
                             │
                ┌────────────┴────────────┐
                │         DOMAIN          │
                │  (Aggregate + Ports)    │
                └────────────┬────────────┘
                             │
                ┌────────────┴────────────┐
                │       OUTBOUND          │
                │  (R2DBC / Memory)       │
                └─────────────────────────┘


### 🔹 Dominio
- `Shoe` → Aggregate Root
- `Sku`, `ShoeId` → Value Objects
- `Money` → Value Object
- `ShoeRepositoryPort` → Puerto

Las reglas de negocio están en el dominio (NO en el controller).

---

## ⚙️ Modos de persistencia

La aplicación soporta 2 modos:

| Modo | Descripción |
|------|-------------|
| `memory` | Base en memoria (ConcurrentHashMap). Ideal para pruebas. |
| `r2dbc` | PostgreSQL reactivo con Flyway. |

Se controla con:

```yaml
app:
  persistence: memory   # o r2dbc
