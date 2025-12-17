# FarmChainX - AI-Driven Agricultural Traceability Network


##  Overview

FarmChainX is a comprehensive farm-to-fork traceability platform that ensures transparency, trust, and food safety throughout the agricultural supply chain. The system enables complete product journey tracking from harvest to consumer, leveraging AI-powered quality grading and blockchain-inspired immutable logging.

## Project Structure

```
farmchainx/
├── src/
│   ├── main/
│   │   ├── java/com/farmchain/farmchain/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── jwt/             # JWT authentication filter
│   │   │   ├── model/           # JPA entities
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── security/        # Security utilities
│   │   │   ├── service/         # Business logic
│   │   │   └── util/            # Utility classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Unit tests
├── uploads/                     # Local file storage
│   └── qrcodes/                # Generated QR codes
├── pom.xml                     # Maven dependencies
└── README.md
```

### Key Capabilities

- **Product Traceability**: Track products from farm to consumer with QR code scanning
- **AI Quality Grading**: Automated quality assessment (A+, A, B+, B, C) based on product images
- **Supply Chain Tracking**: Immutable blockchain-style logging of product movement
- **Role-Based Access**: Different dashboards for Farmers, Distributors, Retailers, Consumers, and Admins
- **Public Verification**: Anyone can scan QR codes to view product journey
- **Feedback System**: Consumer ratings and reviews for transparency

##  Supply Chain Flow

```
┌─────────────┐
│   FARMER    │  1. Uploads product + image
│             │  2. AI grades quality
│             │  3. Generates QR code
└──────┬──────┘
       │
       ↓
┌─────────────┐
│ DISTRIBUTOR │  4. Scans QR and takes possession
│             │  5. Adds transport logs
│             │  6. Hands over to retailer
└──────┬──────┘
       │
       ↓
┌─────────────┐
│  RETAILER   │  7. Receives and confirms
│             │  8. Product ready for sale
└──────┬──────┘
       │
       ↓
┌─────────────┐
│  CONSUMER   │  9. Scans QR code
│             │  10. Views complete journey
│             │  11. Submits feedback
└─────────────┘
```

##  Features

### For Farmers
- Upload crop details (type, soil, pesticides, harvest date, GPS location)
- Upload product images for AI quality grading
- Generate unique QR codes for each product batch
- View all uploaded products with pagination
- Track product journey through supply chain

### For Distributors
- Take possession of products from farmers
- Add in-transit tracking updates
- Hand over products to retailers
- View supply chain history

### For Retailers
- View pending product receipts
- Confirm product reception
- Final supply chain confirmation

### For Consumers
- Scan QR codes to view complete product journey
- See farmer details, harvest information, and quality grades
- View transport and storage logs
- Provide feedback and ratings
- Verify product authenticity

### For Admins
- System-wide analytics dashboard
- User management and role promotion
- Admin access request approval system
- Monitor all products and transactions
- Generate system reports

## Tech Stack

### Backend
- **Framework**: Spring Boot 3.5.6
- **Language**: Java 17
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0
- **ORM**: Hibernate JPA
- **Image Storage**: Cloudinary
- **QR Generation**: ZXing (Google)
- **API Documentation**: Swagger/OpenAPI 3

### Additional Libraries
- **Lombok**: Reduce boilerplate code
- **BCrypt**: Password encryption
- **JJWT**: JWT token handling
- **TwelveMonkeys ImageIO**: Enhanced image format support

### AI Module
- Custom Java-based image analysis service
- Color ratio analysis
- Quality grading algorithm
- Confidence scoring

##  Architecture

### Three-Layer Architecture
```
┌─────────────────────────────────────────┐
│          Controller Layer               │
│     (REST API Endpoints)                │
└─────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────┐
│          Service Layer                  │
│     (Business Logic)                    │
└─────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────┐
│          Repository Layer               │
│     (Data Access - JPA)                 │
└─────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────┐
│          MySQL Database                 │
└─────────────────────────────────────────┘
```



##  User Roles

### ROLE_FARMER
- Upload products with images
- Generate QR codes
- View own products
- Track product journey

### ROLE_DISTRIBUTOR
- Take possession from farmers
- Add in-transit updates
- Hand over to retailers
- View supply chain logs

### ROLE_RETAILER
- View pending receipts
- Confirm product reception
- View supply chain logs

### ROLE_CONSUMER
- Scan QR codes
- View product journey
- Submit feedback and ratings

### ROLE_ADMIN
- Full system access
- User management
- Analytics dashboard
- Approve admin requests




##  Database Schema

### Key Tables

- **users**: User accounts with encrypted passwords
- **roles**: System roles (FARMER, DISTRIBUTOR, RETAILER, CONSUMER, ADMIN)
- **user_roles**: Many-to-many relationship
- **products**: Product information with farmer reference
- **supply_chain_log**: Immutable tracking logs with hash chain
- **feedback**: Consumer ratings and comments
- **admin_promotion_requests**: Admin access requests

## Credits

Developed by Sumit Kumar Mandal

B.tech Computer Science and Engineering-2026

## License

This project is for education and personal use.
