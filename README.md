
## Prueba Técnica Backend
API desarrollada con Spring Boot y WebFlux para la gestión de franquicias, sucursales y productos siguiendo los principios de Arquitectura Limpia (Clean Architecture).

## Características
- Proyecto en **Spring Boot** con **WebFlux** para programación reactiva
- **MongoDB** como base de datos
- **Swagger/OpenAPI** para documentación de la API
- Scaffolding of Clean Architecture

## Configuración de MongoDB
1. **Crear las colecciones necesarias** son:
    - `franchises`
    - `branches`
    - `products`

## Endpoints
### Franquicias
- **POST** `/franchises` - Crear una nueva franquicia
- **GET** `/franchises` - Obtener todas las franquicias
- **GET** `/franchises/{franchiseId}` - Obtener una franquicia por ID
- **GET** `/franchises/{franchiseId}/top-stock` - Obtener productos con mayor stock por cada sucursal

### Sucursales
- **POST** `/franchises/{franchiseId}/branches` - Agregar una sucursal a una franquicia

### Productos
- **POST** `/branches/{branchId}/products` - Agregar un producto a una sucursal
- **DELETE** `/branches/{branchId}/products/{productId}` - Eliminar un producto de una sucursal
- **PUT** `/branches/{branchId}/products/{productId}/stock` - Actualizar el stock de un producto

## Requisitos
- Java 21
- MongoDB
- Gradle

## Ejecución
1. Configurar la conexión en `application.properties`
2. Ejecuta la aplicación
3. Accede a la interfaz de Swagger en: `http://localhost:8080/swagger-ui.html`

