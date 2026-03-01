# Blinkit Clone

## 1. System Architecture
- **Frontend**: React + Redux Toolkit + Tailwind + Axios + React Router.
- **Backend**: Spring Boot REST API with layered packages (`controller`, `service`, `repository`, `dto`, `entity`, `security`, `config`, `exception`).
- **Auth**: JWT based stateless authentication and role-based authorization.
- **Database**: PostgreSQL using JPA/Hibernate.
- **DevOps**: Dockerized frontend, backend, and PostgreSQL with `docker-compose`.

### High-Level Flow
1. User logs in/registers and receives JWT.
2. Frontend stores JWT and sends it in `Authorization: Bearer <token>`.
3. Spring Security validates token and role before API access.
4. Services orchestrate business operations and persist to PostgreSQL.

## 2. Database Schema
Tables implemented in entities:
- `users`
- `addresses`
- `categories`
- `products`
- `cart`
- `cart_items`
- `orders`
- `order_items`
- `deliveries`

Entity fields mirror requested design and relations through JPA mappings.

## 3. Backend Implementation
Backend path: `backend/`
- JWT auth (`/api/auth/register`, `/api/auth/login`)
- Profile APIs (`/api/users/profile`)
- Category APIs (`/api/categories`)
- Product APIs with pagination + search (`/api/products`)
- Cart APIs (`/api/cart`, `/api/cart/add`, `/api/cart/update`, `/api/cart/remove`)
- Orders APIs (`/api/orders`, `/api/orders/my`, `/api/orders/all`)
- Delivery APIs (`/api/delivery/orders`, `/api/delivery/status`)
- Validation + global exception handling
- Swagger UI at `/swagger-ui/index.html`

## 4. Frontend Implementation
Frontend path: `frontend/`
- Pages: Home, Category, Product, Cart, Checkout, Order Success, Order History, Profile, Admin Dashboard, Admin Products, Admin Orders, Delivery Dashboard.
- Components: Navbar, SearchBar, ProductCard.
- Redux Toolkit for product listing and search.
- Axios service configured with JWT interceptor.

## 5. Docker Setup
- `backend/Dockerfile`
- `frontend/Dockerfile`
- `docker-compose.yml` with services:
  - `postgres`
  - `backend`
  - `frontend`

## 6. Run Instructions
### Local backend
```bash
cd backend
mvn spring-boot:run
```

### Local frontend
```bash
cd frontend
npm install
npm run dev
```

### Docker
```bash
docker compose up --build
```

## 7. Deployment Guide
1. Set production environment variables:
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `JWT_SECRET`
   - `CLOUDINARY_KEY` (optional if moving from local image URL strategy)
2. Build images and push to your container registry.
3. Deploy backend + frontend containers to Kubernetes/ECS/VM.
4. Provision managed PostgreSQL and update `DB_URL`.
5. Attach reverse proxy (Nginx/API Gateway), enable HTTPS, and configure CORS.
