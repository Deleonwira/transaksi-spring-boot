# 🛍️ Spring Boot Transaksi API

RESTful API untuk manajemen transaksi penjualan, inventaris, dan pengguna (Karyawan, Pelanggan, Pemasok). Dibangun dengan Spring Boot dan diamankan dengan JWT Authentication.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)
![Nginx](https://img.shields.io/badge/Nginx-Reverse%20Proxy-009639?logo=nginx)

---

## 🚀 Features

- **Role-Based Access Control (RBAC)**:
  - **ADMIN/KARYAWAN**: Full access ke semua resource (CRUD)
  - **PELANGGAN**: Dapat view Products, Product Types, dan Transaksi sendiri
  - **PEMASOK**: Dapat view profil Supplier sendiri
- **Authentication**: Login & registrasi dengan JWT + BCrypt password hashing
- **Transaction Management**: CRUD transaksi dengan detail line items
- **Inventory Management**: Kelola Barang & Jenis Barang dengan tracking stok
- **API Documentation**: Swagger UI untuk eksplorasi API interaktif
- **Docker Ready**: Siap deploy dengan Docker Compose + Nginx

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Runtime |
| Spring Boot | 3.3.0 | Framework |
| MySQL | 8.0 | Database |
| Spring Security | 6 | Authentication & Authorization |
| JWT | - | Token-based Auth |
| Nginx | Alpine | Reverse Proxy |
| Docker | - | Containerization |
| SpringDoc OpenAPI | - | API Documentation |

---

## 🏗️ Architecture

```
                    ┌─────────────┐
    Internet ──────►│   Nginx     │:80
                    │  (Reverse   │
                    │   Proxy)    │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │ Spring Boot │:8080 (internal)
                    │    App      │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │   MySQL     │:3306
                    └─────────────┘
```

---

## ⚙️ Setup & Installation

### Option 1: Docker Compose (Recommended) 🐳

```bash
# Clone repository
git clone <repository-url>
cd transaksi-spring-boot

# Build dan jalankan semua services
docker compose up --build

# Atau jalankan di background
docker compose up --build -d
```

**Akses setelah running:**
| Service | URL |
|---------|-----|
| API (via Nginx) | http://localhost |
| Swagger UI | http://localhost/swagger-ui/index.html |
| phpMyAdmin | http://localhost:8081 |

### Option 2: Local Development 💻

**Prerequisites:**
- Java JDK 21+
- MySQL Server
- Maven (atau gunakan Maven Wrapper)

**1. Setup Database:**
```bash
# Buat database dan import data
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS penjualan"
mysql -u root -p penjualan < db/penjualan.sql
```

**2. Konfigurasi:**

Edit `src/main/resources/application-local.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/penjualan
    username: root
    password: # your_password
```

**3. Jalankan Aplikasi:**
```bash
# Windows
./mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local

# Linux/Mac
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

---

## 🔐 Testing & Authentication

### Default User Credentials

Semua user menggunakan password yang sama: **`password123`**

| Username | Password | Role | Deskripsi |
|----------|----------|------|-----------|
| `admin` | `password123` | ADMIN | Administrator (Karyawan Tuti) |
| `siti` | `password123` | KARYAWAN | Staff/Kasir |
| `endang` | `password123` | KARYAWAN | Staff/Kasir |
| `hasan` | `password123` | KARYAWAN | Staff/Kasir |
| `andi` | `password123` | PELANGGAN | Customer |
| `susanto` | `password123` | PELANGGAN | Customer |
| `merry` | `password123` | PELANGGAN | Customer |
| `pungkas` | `password123` | PEMASOK | Supplier |
| `djati` | `password123` | PEMASOK | Supplier |

### Testing dengan Postman

#### 1. Login untuk Mendapatkan Token

**Request:**
```http
POST http://localhost/api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "password123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "role": "ADMIN"
}
```

#### 2. Gunakan Token untuk Akses API

Tambahkan header `Authorization` di setiap request:
```
Authorization: Bearer <token_dari_login>
```

#### 3. Contoh API Endpoints

**Get All Barang:**
```http
GET http://localhost/api/barang
Authorization: Bearer <token>
```

**Create Transaksi:**
```http
POST http://localhost/api/transaksi
Authorization: Bearer <token>
Content-Type: application/json

{
    "idPelanggan": "P001",
    "idKaryawan": "K001"
}
```

**Get All Transaksi:**
```http
GET http://localhost/api/transaksi
Authorization: Bearer <token>
```

### Testing dengan cURL

```bash
# Login
curl -X POST http://localhost/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'

# Get Barang (ganti TOKEN dengan token dari login)
curl http://localhost/api/barang \
  -H "Authorization: Bearer TOKEN"

# Register User Baru
curl -X POST http://localhost/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"newuser","password":"password123","role":"PELANGGAN"}'
```

---

## 📖 API Documentation

Swagger UI tersedia di:
- **Docker**: http://localhost/swagger-ui/index.html
- **Local**: http://localhost:8080/swagger-ui/index.html

### Available Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| POST | `/api/auth/login` | Login user | Public |
| POST | `/api/auth/register` | Register user baru | Public |
| GET | `/api/barang` | List semua barang | ADMIN, KARYAWAN, PELANGGAN |
| POST | `/api/barang` | Tambah barang baru | ADMIN, KARYAWAN |
| GET | `/api/jenis-barang` | List jenis barang | ADMIN, KARYAWAN, PELANGGAN |
| GET | `/api/transaksi` | List transaksi | ADMIN, KARYAWAN, PELANGGAN |
| POST | `/api/transaksi` | Buat transaksi baru | ADMIN, KARYAWAN, PELANGGAN |
| GET | `/api/karyawan` | List karyawan | ADMIN, KARYAWAN |
| GET | `/api/pelanggan` | List pelanggan | ADMIN, KARYAWAN |
| GET | `/api/pemasok` | List pemasok | ADMIN, KARYAWAN, PEMASOK |

---

## 📁 Project Structure

```
transaksi-spring-boot/
├── .github/
│   └── workflows/
│       ├── maven.yml           # CI: Build & Test dengan Maven
│       └── docker-compose.yml  # CI: Build & Test dengan Docker
├── db/
│   └── penjualan.sql           # Database schema & seed data
├── nginx/
│   └── nginx.conf              # Nginx reverse proxy config
├── src/main/java/.../transaksi/
│   ├── barang/                 # Product management
│   ├── detailtransaksi/        # Transaction line items
│   ├── jenisbarang/            # Product categories
│   ├── karyawan/               # Employee management
│   ├── pelanggan/              # Customer management
│   ├── pemasok/                # Supplier management
│   ├── pengguna/               # User accounts & RBAC
│   ├── security/               # JWT Auth & Security Config
│   ├── transaksi/              # Transaction headers
│   └── TransaksiApplication.java
├── docker-compose.yml          # Docker orchestration
├── Dockerfile                  # App container build
└── pom.xml                     # Maven dependencies
```

---

## 🐳 Docker Services

| Container | Image | Port | Description |
|-----------|-------|------|-------------|
| nginx-proxy | nginx:alpine | 80 | Reverse proxy |
| transaksi-app | (build) | 8080 (internal) | Spring Boot API |
| mysql-db | mysql:8.0 | 3306 | Database |
| mysql-init | mysql:8.0 | - | DB initialization |
| phpmyadmin | phpmyadmin | 8081 | Database GUI |

### Docker Commands

```bash
# Start semua services
docker compose up --build -d

# Lihat logs
docker compose logs -f app

# Stop semua services
docker compose down

# Stop dan hapus volumes (reset database)
docker compose down -v

# Rebuild hanya app
docker compose up --build -d app
```

---

## 🔧 Environment Profiles

| Profile | File | Usage |
|---------|------|-------|
| `local` | application-local.yaml | Development lokal |
| `docker` | application-docker.yaml | Docker container |

---

## 📝 License

Created for CCIT FTUI Spring Boot Project.
