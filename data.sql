DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS customers;

-- Users table
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

-- Orders table
CREATE TABLE orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    order_date TEXT DEFAULT (datetime('now')),
    status TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT UNIQUE
);


-- Products table
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    category TEXT,
    price REAL NOT NULL,
    quantity INTEGER NOT NULL,
    date TEXT
);

-- Sample products data
INSERT INTO products VALUES (1, 'Database System Concepts', 'Books', 9.99, 50, '2025-01-01');
INSERT INTO products VALUES (2, 'dress', 'Clothing', 29.99, 10, '2025-01-02');
INSERT INTO products VALUES (3, 'Spone', 'Home & Kitchen', 49.99, 75, '2025-01-03');
INSERT INTO products VALUES (4, 'Ring', 'Jewellery', 15.99, 30, '2025-01-04');
INSERT INTO products VALUES (5, 'Slipper', 'Shoe', 5.99, 20, '2025-01-30');
INSERT INTO products VALUES (6, 'Ball', 'Toys', 250.00, 20, '2025-01-30');

-- Order items table
CREATE TABLE order_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER NOT NULL,
    product_name TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    price REAL NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);
