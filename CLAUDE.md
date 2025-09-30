# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

CRM-Busto is a Java-based product catalog management system that synchronizes product data from the Imperdiel API to a local SQLite database. The application downloads product information (articulos), processes it, and stores it locally for querying.

## Build and Run Commands

- **Run application**: `./mvnw exec:java` or `mvn exec:java`
- **Compile**: `./mvnw compile` or `mvn compile`
- **Clean build**: `./mvnw clean package` or `mvn clean package`
- **Entry point**: `api.NewMain` (configured in pom.xml)

Note: Use `mvnw.cmd` instead of `./mvnw` on Windows.

## Architecture

### Three-Layer Architecture

1. **API Layer** (`src/main/java/api/`)
   - `AuthenticationService`: Handles authentication with Imperdiel API, retrieves bearer tokens
   - `JsonDownloader`: Downloads product data from API as JSON, saves to `src/main/resources/Articulos.json`
   - `JsonMapper`: Parses JSON using Jackson, maps nested JSON structure to domain models
   - `DataBaseUpdate`: Orchestrates the sync process from JSON to database
   - `NewMain`: Application entry point

2. **DAO Layer** (`src/main/java/dao/`)
   - `DatabaseConnection`: Singleton pattern for SQLite connection management
   - Database auto-extracts from resources to `~/.consultorimperdiel/ConsultorImperdielDB.sqlite`
   - DAO interfaces (`IArticuloDao`, `IMarcaDao`) and implementations for data access

3. **Models** (`src/main/java/models/`)
   - `Articulo`: Product entity with pricing (neto, IVA, costo), marca, rubro
   - `Marca`: Brand/manufacturer entity
   - `Rubro`: Category entity

### Data Flow

1. `AuthenticationService` authenticates with API endpoint
2. `JsonDownloader` fetches product catalog using bearer token
3. `JsonMapper` parses JSON into domain objects
4. `DataBaseUpdate` performs insert-or-update operations with transaction management
5. Database schema enforces referential integrity (articulos → marcas, rubros)

### Database Schema

- **articulos**: id, codigo, precio_neto, precio_iva, precio_costo, nombre, descripcion, marca_id (FK), rubro_id (FK)
- **marcas**: id, nombre
- **rubros**: id, nombre

## Key Technical Details

- **Java Version**: 21
- **Dependencies**: SQLite JDBC driver, Jackson for JSON processing
- **Database Location**: User home directory (`~/.consultorimperdiel/`)
- **API Credentials**: Hardcoded in `AuthenticationService` (usuario: "cod40", clave: "cod40")
- **Transaction Management**: Uses manual commit/rollback in `DataBaseUpdate` for data integrity
- **Price Calculation**: PrecioIva = PrecioNeto × 1.21, PrecioCosto = PrecioIva / 2

## Common Development Tasks

To update the database with latest products, uncomment line 35 in `NewMain.java`:
```java
dataBaseUpdate.updateArticulos();
```
