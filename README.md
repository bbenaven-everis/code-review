# code-review

### Requisitos
- Java 17
- PostgreSQL (db: shoe_store)

### Crear DB
CREATE DATABASE shoe_store;

### Configurar credenciales
Ver src/main/resources/application.yml

### Ejecutar
mvn spring-boot:run

### Endpoints
POST   /api/shoes
GET    /api/shoes
GET    /api/shoes/{id}
PUT    /api/shoes/{id}
DELETE /api/shoes/{id}