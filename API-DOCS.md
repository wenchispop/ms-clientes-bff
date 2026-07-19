# 📚 Documentación API - Gestión de Clientes

## 🏨 Descripción General
API BFF (Backend For Frontend) para gestionar clientes del **Hotel de Gatos**.

**Base URL:** `W://localhost:8180/api/v1`

---

## 🔗 Acceso a Swagger UI
- **URL Local:** http://localhost:8180/swagger-ui.html
- **JSON API:** http://localhost:8180/v3/api-docs

---

## 📋 Endpoints

### 1️⃣ LISTAR TODOS LOS CLIENTES
```
GET /api/v1/clientes
```

**Descripción:** Obtiene la lista completa de clientes registrados

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan Pérez",
    "email": "juan@email.com",
    "telefono": "+56912345678",
    "direccion": "Calle Principal 123, Apt 4B"
  },
  {
    "id": 2,
    "nombre": "María García",
    "email": "maria@email.com",
    "telefono": "+56987654321",
    "direccion": "Avenida Secundaria 456"
  }
]
```

---

### 2️⃣ CREAR UN NUEVO CLIENTE
```
POST /api/v1/clientes
```

**Descripción:** Crea un nuevo cliente (ID generado automáticamente)

**Request Body:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "telefono": "+56912345678",
  "direccion": "Calle Principal 123, Apt 4B"
}
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "telefono": "+56912345678",
  "direccion": "Calle Principal 123, Apt 4B"
}
```

**Validaciones:**
- `nombre`: Obligatorio, máx 100 caracteres
- `email`: Obligatorio, formato válido, máx 150 caracteres
- `telefono`: Opcional, máx 20 caracteres
- `direccion`: Opcional, máx 255 caracteres

---

### 3️⃣ OBTENER CLIENTE POR ID
```
GET /api/v1/clientes/{id}
```

**Parámetros:**
- `id` (Path): ID del cliente (número entero)

**Ejemplo:** `GET /api/v1/clientes/1`

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "telefono": "+56912345678",
  "direccion": "Calle Principal 123, Apt 4B"
}
```

**Errores:**
- `404 Not Found`: Cliente no existe

---

### 4️⃣ ACTUALIZAR CLIENTE
```
PUT /api/v1/clientes/{id}
```

**Parámetros:**
- `id` (Path): ID del cliente a actualizar

**Request Body:** (Puedes actualizar solo los campos que necesites)
```json
{
  "nombre": "Juan Pérez Updated",
  "email": "juannuevo@email.com",
  "telefono": "+56912345678",
  "direccion": "Nueva calle 789"
}
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez Updated",
  "email": "juannuevo@email.com",
  "telefono": "+56912345678",
  "direccion": "Nueva calle 789"
}
```

**Errores:**
- `404 Not Found`: Cliente no existe
- `400 Bad Request`: Datos inválidos

---

### 5️⃣ ELIMINAR CLIENTE
```
DELETE /api/v1/clientes/{id}
```

**Parámetros:**
- `id` (Path): ID del cliente a eliminar

**Ejemplo:** `DELETE /api/v1/clientes/1`

**Respuesta (204 No Content):**
- No retorna body, solo confirma la eliminación

**Errores:**
- `404 Not Found`: Cliente no existe

---

## 📊 Modelo de Datos - ClienteDTO

| Campo | Tipo | Obligatorio | Rango | Descripción |
|-------|------|-------------|-------|-------------|
| `id` | Long | No | - | ID único (generado automáticamente) |
| `nombre` | String | Sí | Máx 100 | Nombre completo del cliente |
| `email` | String | Sí | Máx 150 | Email válido para contacto |
| `telefono` | String | No | Máx 20 | Teléfono de contacto |
| `direccion` | String | No | Máx 255 | Dirección del cliente |

---

## 🛠️ Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| `200` | OK - Solicitud exitosa |
| `201` | Created - Recurso creado |
| `204` | No Content - Eliminado exitosamente |
| `400` | Bad Request - Datos inválidos |
| `404` | Not Found - Recurso no encontrado |
| `500` | Internal Server Error - Error del servidor |

---

## 💡 Ejemplos de Uso con cURL

### Listar clientes
```bash
curl -X GET "http://localhost:8080/api/v1/clientes"
```

### Crear cliente
```bash
curl -X POST "http://localhost:8080/api/v1/clientes" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Carlos López",
    "email": "carlos@email.com",
    "telefono": "+56911111111",
    "direccion": "Av. Principal 999"
  }'
```

### Obtener cliente
```bash
curl -X GET "http://localhost:8080/api/v1/clientes/1"
```

### Actualizar cliente
```bash
curl -X PUT "http://localhost:8080/api/v1/clientes/1" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Carlos López Updated",
    "email": "carlos.updated@email.com"
  }'
```

### Eliminar cliente
```bash
curl -X DELETE "http://localhost:8080/api/v1/clientes/1"
```

---

## ⚙️ Tecnologías Utilizadas

- **Spring Boot 4.1.0**
- **Spring Cloud OpenFeign**
- **SpringDoc OpenAPI 2.6.0** (Swagger)
- **Lombok**
- **Jakarta Validation**
- **Java 17**

---

## 📝 Notas Importantes

1. **ID Auto-generado:** Al crear un cliente, el `id` se genera automáticamente en la BD
2. **Validaciones:** El servidor valida todos los datos según las restricciones indicadas
3. **Email único:** Se recomienda validar que cada email sea único (verificar en el BS)
4. **Soft Delete:** Verificar si la eliminación es física o lógica en tu sistema

---

## 🔄 Flujo Típico

1. Crear cliente → POST → ID generado
2. Listar clientes → GET
3. Obtener cliente específico → GET /{id}
4. Actualizar datos → PUT /{id}
5. Eliminar cliente → DELETE /{id}

---

**Última actualización:** Julio 2026
