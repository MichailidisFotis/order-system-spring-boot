# Java Spring Boot API

A comprehensive RESTful API built with Java Spring Boot for managing an e-commerce system with authentication, customers, products, categories, and orders.

## Table of Contents

- [Overview](#overview)
- [Base URL](#base-url)
- [Authentication](#authentication)
- [Quick Reference - All Endpoints](#quick-reference---all-endpoints)
- [API Endpoints](#api-endpoints)
    - [Authentication](#authentication-endpoints)
    - [Users](#users-endpoints)
    - [Customers](#customers-endpoints)
    - [Categories](#categories-endpoints)
    - [Products](#products-endpoints)
    - [Orders](#orders-endpoints)
- [Setup](#setup)
- [Usage Examples](#usage-examples)

## Overview

This API provides a complete backend solution for an e-commerce platform with the following features:

- User authentication (register, login, logout)
- Customer management
- Product catalog with categories
- Order processing and state management
- UUID-based resource identification

## Base URL

```
http://localhost:8080
```

## Authentication

The API uses email/password authentication. Users must register or login to access protected endpoints.

### Authentication Flow

1. **Register** a new user account
2. **Login** to receive authentication credentials
3. **Logout** when done

---

## Quick Reference - All Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login to the system |
| POST | `/auth/logout` | Logout from the system |

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users` | Get all users |

### Customers
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/customers` | Create a new customer |
| GET | `/customers` | Get all customers |
| GET | `/customers/{customer_id}` | Get customer by ID |
| PATCH | `/customers/{customer_id}` | Update customer |
| DELETE | `/customers/{customer_id}` | Delete customer |

### Categories
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/categories` | Create a new category |
| GET | `/categories` | Get all categories |
| GET | `/categories/search/{category_id}` | Get category by ID |
| GET | `/categories/search?description={description}` | Get category by description |
| PATCH | `/categories/{category_id}` | Update category |
| DELETE | `/categories/{category_id}` | Delete category |

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/products` | Create a new product |
| GET | `/products` | Get all products |
| GET | `/products/{product_id}` | Get product by ID |
| PATCH | `/products/{product_id}` | Update product |
| PATCH | `/products/update-category/{product_id}?category_id={category_id}` | Update product category |
| DELETE | `/products/{product_id}` | Delete product |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders` | Create a new order |
| GET | `/orders` | Get all orders |
| GET | `/orders/{order_id}` | Get order by ID |
| GET | `/orders/get-order-by-state/{state}` | Get orders by state |
| GET | `/orders/get-order-products/{order_id}` | Get products in an order |
| PATCH | `/orders/change-state/{order_id}` | Change order state/Complete order |
| DELETE | `/orders/{order_id}` | Delete order |

**Total Endpoints: 30**

---

## API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /auth/register
```

**Request Body:**
```json
{
  "firstname": "Fotis",
  "surname": "Michailidis",
  "email": "fotis@gmail.com",
  "password": "1234"
}
```

#### Login
```http
POST /auth/login
```

**Request Body:**
```json
{
  "email": "fotis@gmail.com",
  "password": "1234"
}
```

#### Logout
```http
POST /auth/logout
```

**Request Body:**
```json
{
  "email": "fotis@gmail.com",
  "password": "1234"
}
```

---

### Users Endpoints

#### Get All Users
```http
GET /users
```

---

### Customers Endpoints

#### Create Customer
```http
POST /customers
```

**Request Body:**
```json
{
  "firstname": "Maria",
  "surname": "Papadopoulou",
  "email": "maria@gmail.com",
  "address": "El. Venizelou 5, Veroia"
}
```

#### Get All Customers
```http
GET /customers
```

#### Get Customer by ID
```http
GET /customers/{customer_id}
```

**Example:**
```
GET /customers/c7fdfd33-0ddc-4a4d-ad1b-f955c688544b
```

#### Update Customer
```http
PATCH /customers/{customer_id}
```

**Request Body (partial update):**
```json
{
  "firstname": "Marianthi",
  "email": "maria"
}
```

#### Delete Customer
```http
DELETE /customers/{customer_id}
```

---

### Categories Endpoints

#### Create Category
```http
POST /categories
```

**Request Body:**
```json
{
  "description": "monitors"
}
```

#### Get All Categories
```http
GET /categories
```

#### Get Category by ID
```http
GET /categories/search/{category_id}
```

**Example:**
```
GET /categories/search/3d4f86c0-030a-48cb-a33d-134329d1b437
```

#### Get Category by Description
```http
GET /categories/search?description={description}
```

**Example:**
```
GET /categories/search?description=Books
```

#### Update Category
```http
PATCH /categories/{category_id}
```

**Request Body:**
```json
{
  "description": "Desktops"
}
```

#### Delete Category
```http
DELETE /categories/{category_id}
```

---

### Products Endpoints

#### Create Product
```http
POST /products
```

**Request Body:**
```json
{
  "description": "Samsung Olympus 27''",
  "brand": "Samsung",
  "price": 300.0,
  "category_id": "c73e0d4b-1f3b-4983-a95c-51315c030a40"
}
```

#### Get All Products
```http
GET /products
```

#### Get Product by ID
```http
GET /products/{product_id}
```

**Example:**
```
GET /products/22689b18-3c07-497a-a4ec-e3f495fc137a
```

#### Update Product
```http
PATCH /products/{product_id}
```

**Request Body (partial update):**
```json
{
  "description": "Asus Laptop 15.5''",
  "brand": "Asus"
}
```

#### Update Product Category
```http
PATCH /products/update-category/{product_id}?category_id={category_id}
```

**Example:**
```
PATCH /products/update-category/22689b18-3c07-497a-a4ec-e3f495fc137a?category_id=fd7136de-6a4e-4cad-b175-c5378b9065e5
```

#### Delete Product
```http
DELETE /products/{product_id}
```

---

### Orders Endpoints

#### Create Order
```http
POST /orders
```

**Request Body:**
```json
{
  "customer_id": "1e3635e8-2aca-4752-9b4f-e7c469e7d23b",
  "payment_method": "cash",
  "products": [
    {
      "product_id": "357ad7ce-0876-4033-a7d0-a0af0492df7f",
      "quantity": 3
    },
    {
      "product_id": "99744f73-17b4-492d-a988-eaa59c2943d6",
      "quantity": 2
    }
  ]
}
```

#### Get All Orders
```http
GET /orders
```

#### Get Order by ID
```http
GET /orders/{order_id}
```

**Example:**
```
GET /orders/5d3b70ab-667f-4374-86ce-2ce82faeb732
```

#### Get Orders by State
```http
GET /orders/get-order-by-state/{state}
```

**Example:**
```
GET /orders/get-order-by-state/created
```

**Available States:**
- `created`
- `completed` (or other custom states)

#### Change Order State (Complete Order)
```http
PATCH /orders/change-state/{order_id}
```

**Example:**
```
PATCH /orders/change-state/4c1c671c-1916-4c99-b962-ff3c026afacd
```

#### Get Order Products
```http
GET /orders/get-order-products/{order_id}
```

**Example:**
```
GET /orders/get-order-products/4c1c671c-1916-4c99-b962-ff3c026afacd
```

#### Delete Order
```http
DELETE /orders/{order_id}
```

---

## Setup

### Prerequisites

- Java 11 or higher
- Maven or Gradle
- Spring Boot
- Database (PostgreSQL, MySQL, or H2)

### Installation

1. Clone the repository
2. Configure your database connection in `application.properties`
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   ./gradlew bootRun
   ```

4. The API will be available at `http://localhost:8080`

### Import Postman Collection

1. Open Postman
2. Click **Import**
3. Select the `Java Spring Boot.postman_collection.json` file
4. All endpoints will be available in your Postman workspace

---

## Usage Examples

### Creating a Complete Order Flow

1. **Register a user:**
   ```bash
   POST /auth/register
   ```

2. **Create a customer:**
   ```bash
   POST /customers
   ```

3. **Create a category:**
   ```bash
   POST /categories
   ```

4. **Create products:**
   ```bash
   POST /products
   ```

5. **Create an order:**
   ```bash
   POST /orders
   ```

6. **Complete the order:**
   ```bash
   PATCH /orders/change-state/{order_id}
   ```

---

## Notes

- All IDs use UUID format
- Request bodies should be sent as JSON with `Content-Type: application/json`
- PATCH endpoints support partial updates (only send fields you want to update)
- Orders can contain multiple products with quantities
- Products must be associated with a valid category

---

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please contact the development team.