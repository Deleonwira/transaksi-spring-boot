# Spring Boot Transaksi API 🛍️

A RESTful API for managing sales transactions, inventory, and users (Employees, Customers, Suppliers). Built with Spring Boot and secured with JWT Authentication.

## 🚀 Features

*   **Role-Based Access Control (RBAC)**:
    *   **ADMIN/KARYAWAN**: Full access to all resources (CRUD).
    *   **PELANGGAN**: Can view Products, Product Types, and their own Transactions/History.
    *   **PEMASOK**: Can view their own Supplier profile.
*   **Authentication**: Secure login and registration with JWT (JSON Web Tokens) & BCrypt password hashing.
*   **Transaction Management**: Create, read, update, and delete transactions with detailed line items.
*   **Inventory Management**: Manage Products (`Barang`) and Product Types (`JenisBarang`) with stock tracking.
*   **API Documentation**: Integrated Swagger UI for interactive API exploration.

## 🛠️ Tech Stack

*   **Java**: 21
*   **Framework**: Spring Boot 3.3.0
*   **Database**: MySQL
*   **ORM**: Hibernate / Spring Data JPA
*   **Security**: Spring Security 6 + JWT
*   **Docs**: SpringDoc OpenAPI (Swagger UI)

## ⚙️ Setup & Installation

### Prerequisites
*   Java JDK 21+
*   MySQL Server

### Database Setup
1.  Create a database named `penjualan`.
2.  Import the SQL schema and data from `db/penjualan.sql`.
    ```bash
    mysql -u root -p penjualan < db/penjualan.sql
    ```

### Configuration
Check `src/main/resources/application-local.yaml` (or `application.properties`) to match your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/penjualan
    username: root
    password: # your_password
```

### Running the App
Use the Maven Wrapper to run the application:

```bash
# Windows
./mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

## 📖 API Documentation

Once the application is running, you can access the interactive Swagger UI documentation at:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Testing with Postman
A Postman collection is included in the project under `postman/penjualan_api_collection.json`.

**Authentication Flow:**
1.  **Register** (Optional): `POST /api/auth/register`
2.  **Login**: `POST /api/auth/login` (Returns a JWT Token)
3.  **Access Protected Endpoints**: Add the token to the `Authorization` header: `Bearer <your_token>`

## 📁 Project Structure

```
src/main/java/ui/ft/ccit/faculty/transaksi/
├── barang/           # Product management
├── detailtransaksi/  # Transaction line items
├── jenisbarang/      # Product categories
├── karyawan/         # Employee management
├── pelanggan/        # Customer management
├── pemasok/          # Supplier management
├── pengguna/         # User accounts & RBAC
├── security/         # JWT Auth & Security Config
├── transaksi/        # Transaction headers
└── App.java          # Main entry point
```

---
*Created for Spring Boot Transaksi Project.*
