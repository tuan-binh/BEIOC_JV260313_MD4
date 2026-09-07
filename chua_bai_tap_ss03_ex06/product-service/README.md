# Product Service

REST API lấy giá và kiểm tra tồn kho sản phẩm, sử dụng Spring Boot, Spring Data JPA,
Flyway và PostgreSQL.

## Chạy ứng dụng

Khởi động PostgreSQL:

```powershell
docker compose up -d
```

Chạy service:

```powershell
.\gradlew.bat bootRun
```

Mặc định ứng dụng kết nối tới `jdbc:postgresql://localhost:5432/product_db` bằng
tài khoản `postgres/postgres`. Có thể thay đổi bằng các biến môi trường
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD` và `SERVER_PORT`.

Flyway tự tạo bảng `products` và ba sản phẩm mẫu trong lần chạy đầu tiên.

## API

Lấy giá sản phẩm:

```http
GET /api/products/1/price
```

Kiểm tra tồn kho (tham số `quantity` mặc định là `1`):

```http
GET /api/products/1/stock?quantity=2
```

Ví dụ phản hồi tồn kho:

```json
{
  "productId": 1,
  "productName": "Laptop Dell Inspiron 15",
  "requestedQuantity": 2,
  "availableQuantity": 10,
  "inStock": true
}
```
