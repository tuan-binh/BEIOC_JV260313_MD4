CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(19, 2) NOT NULL CHECK (price >= 0),
    stock_quantity INTEGER NOT NULL CHECK (stock_quantity >= 0)
);

INSERT INTO products (name, price, stock_quantity) VALUES
    ('Laptop Dell Inspiron 15', 18990000.00, 10),
    ('Chuột Logitech M331', 349000.00, 25),
    ('Bàn phím Keychron K2', 2190000.00, 0);
