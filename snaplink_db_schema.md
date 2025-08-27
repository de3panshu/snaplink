
# 📂 SnapLink Database Schema (v1)

This document describes the core database tables for the SnapLink URL Shortener backend.

---

## 1️⃣ ShortUrl
Stores the short code for each shortened URL.

| Column         | Type   | Description |
|----------------|--------|-------------|
| id             | INT    | Primary key for internal reference. |
| short_url      | TEXT   | Unique short identifier appended to domain (e.g., snap.li/xyz123). |
| creator_user_id| UUID   | Foreign key to User.id. NULL for guest users. |

---

## 2️⃣ OriginalUrl
Maps original URLs to short URLs and supports geo-based redirection.

| Column             | Type         | Description |
|--------------------|--------------|-------------|
| id                 | INT          | Primary key. |
| url_id             | INT          | Foreign key to ShortUrl.id. |
| original_url       | TEXT         | The full original URL. |
| created_at         | TIMESTAMP    | When the link was created. |
| expiration_date    | TIMESTAMP    | Expiry date for link (default: 2 years). |
| status             | INT          | Bit-level status flags (e.g., affiliated, suspended). |
| meta_title         | VARCHAR(255) | Title of the original page for preview cards. |
| meta_description   | TEXT         | Description for preview cards. |
| geographical_code  | INT          | Bit-level geo code to manage location-specific redirection. |

---

## 3️⃣ UrlHit
Tracks every click on a short URL with geo and device data.

| Column        | Type         | Description |
|---------------|--------------|-------------|
| id            | UUID         | Unique ID per click event. |
| original_url_id| INT         | Foreign key to OriginalUrl.id. |
| timestamp     | TIMESTAMP    | Exact date/time of the click. |
| ip_address    | VARCHAR(45)  | Used for geo-location and fraud detection. |
| user_agent    | TEXT         | Browser/device info. |
| referrer      | TEXT         | Source of the click (marketing analytics). |
| country       | INT          | Geo analytics - country. |
| region        | INT          | Geo analytics - region/state. |
| city          | INT          | Geo analytics - city. |
| latitude      | DECIMAL(9,6) | Click latitude. |
| longitude     | DECIMAL(9,6) | Click longitude. |
| device_type   | VARCHAR(50)  | Desktop/Mobile/Tablet classification. |

---

## 4️⃣ User
Stores registered user accounts.

| Column          | Type         | Description |
|-----------------|--------------|-------------|
| id              | UUID         | Primary key. |
| username        | VARCHAR(50)  | Unique username for branding. |
| email           | VARCHAR(100) | Unique email for login and verification. |
| password_hash   | TEXT         | Securely hashed password. |
| role            | VARCHAR(20)  | USER / ADMIN / GUEST. |
| created_at      | TIMESTAMP    | Account creation date. |
| last_login      | TIMESTAMP    | Last login timestamp. |
| user_plan_id    | INT          | Foreign key to UserPlan.id. |

---

## 5️⃣ Plan
Defines subscription plans and core features.

| Column             | Type         | Description |
|--------------------|--------------|-------------|
| id                 | UUID         | Primary key. |
| name               | VARCHAR(50)  | Plan name (Free, Pro, Business). |
| version            | INT          | Plan version for immutability. |
| max_links          | INT          | Max links allowed in this plan. |
| max_custom_domains | INT          | Max branded domains allowed. |
| support_level      | VARCHAR(20)  | EMAIL, CHAT, PRIORITY. |
| features           | JSONB        | JSON storing plan feature flags. |
| status             | VARCHAR(20)  | ACTIVE / INACTIVE. |
| created_at         | TIMESTAMP    | Plan creation timestamp. |

---

## 6️⃣ PlanPricing
Stores billing cycles, offers, and country-wise pricing using JSONB.

| Column          | Type         | Description |
|-----------------|--------------|-------------|
| id              | UUID         | Primary key. |
| plan_id         | UUID         | Foreign key to Plan.id. |
| billing_cycle   | VARCHAR(20)  | WEEKLY, MONTHLY, QUARTERLY, ANNUALLY. |
| pricing         | JSONB        | JSON containing default & country-specific pricing. |
| offer_details   | TEXT         | Human-readable description of offers. |
| created_at      | TIMESTAMP    | When the pricing record was added. |

Example JSON for `pricing`:
```json
{
  "default": { "currency": "USD", "actual_price": 10.00, "offer_price": 8.00 },
  "IN": { "currency": "INR", "actual_price": 499.00, "offer_price": 299.00 }
}
```

---

## 7️⃣ UserPlan
Tracks active subscriptions per user.

| Column             | Type         | Description |
|--------------------|--------------|-------------|
| id                 | UUID         | Primary key. |
| user_id            | UUID         | Foreign key to User.id. |
| plan_id            | UUID         | Foreign key to Plan.id. |
| start_date         | TIMESTAMP    | Subscription start date. |
| end_date           | TIMESTAMP    | Subscription end date. |
| auto_renew         | BOOLEAN      | Whether plan auto-renews. |
| status             | VARCHAR(20)  | ACTIVE / CANCELLED / EXPIRED. |
| last_payment_date  | TIMESTAMP    | Last payment timestamp. |
| next_payment_date  | TIMESTAMP    | Next payment timestamp. |

---

## 8️⃣ UserPlanLog
Audit trail for every plan subscription event.

| Column             | Type         | Description |
|--------------------|--------------|-------------|
| id                 | UUID         | Primary key. |
| user_id            | UUID         | Foreign key to User.id. |
| plan_id            | UUID         | Foreign key to Plan.id. |
| action             | VARCHAR(20)  | SUBSCRIBED, CANCELLED, EXPIRED, UPGRADED. |
| start_date         | TIMESTAMP    | When this plan started. |
| end_date           | TIMESTAMP    | When this plan ended. |
| price_at_purchase  | DECIMAL(10,2)| Captures price at time of purchase. |
| features_snapshot  | JSONB        | Snapshot of plan features at subscription time. |
| created_at         | TIMESTAMP    | Log entry timestamp. |
