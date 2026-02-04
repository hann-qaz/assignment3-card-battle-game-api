# Assignment 4 — SOLID Architecture & Advanced OOP Refactoring (Milestone 2)

## Overview
This repository contains a refactored version of **Assignment 3**: a REST-like **Java API** for **Clash Royale–style game entities**, implemented with **JDBC** and **PostgreSQL**.  
Milestone 2 focuses on improving the design through **SOLID principles**, a stricter **layered architecture**, and additional **advanced OOP / Java features** while keeping the same general topic and database approach.

**Main goals in this milestone:**
- Refactor the codebase to a clean **controller → service → repository → database** flow
- Apply **SOLID** to improve maintainability, extensibility, and testability
- Demonstrate advanced Java features: **interfaces (default/static methods), generics, lambdas, reflection (RTTI)**
- Keep **JDBC + PostgreSQL** persistence and improve schema quality where needed

---

## Key Features
- Multi-layer architecture with clear responsibilities per layer
- Abstract base entity with multiple subclasses (polymorphism)
- Composition/aggregation modeled in both OOP and database schema
- Generic CRUD repository abstraction (generics)
- Service-layer validation and business rules with consistent exception handling
- Utility tooling for reflection inspection (RTTI) and lambda-based sorting/filtering

---

## SOLID Principles Documentation

### SRP — Single Responsibility Principle
Each class/module is designed to do one job:
- **Controllers**: accept input, call service methods, present results (no business rules)
- **Services**: validation + business logic + orchestration of repositories
- **Repositories**: JDBC persistence only (CRUD + mapping), no business decisions
- **Models/DTOs**: represent data structures and transfer objects
- **Utils**: reusable helpers such as reflection and sorting

### OCP — Open/Closed Principle
The domain model supports adding new types (subclasses) without rewriting existing logic:
- An **abstract base entity** defines shared fields and required behaviors
- New subclasses can be introduced by extending the base class and implementing abstract methods
- Service logic relies on abstractions and polymorphism rather than type-check branching

### LSP — Liskov Substitution Principle
Subclasses are designed to be safely used through the base type:
- Each subclass correctly fulfills base-class contracts
- Overridden methods behave consistently and do not weaken preconditions or break invariants
- Code operating on the abstract base type does not need to “special-case” child classes

### ISP — Interface Segregation Principle
Interfaces remain small and focused:
- Multiple narrow interfaces are preferred over one large “god interface”
- Each interface includes at least one abstract method, and at least one interface demonstrates:
    - a **default method**
    - a **static method**
      This keeps consumers dependent only on capabilities they actually use.

### DIP — Dependency Inversion Principle
High-level modules do not depend on low-level implementations:
- Controllers depend on **service interfaces**
- Services depend on **repository interfaces**
- Concrete implementations are injected (commonly via constructors), enabling easier testing and replacement

---

## Core OOP Design

### Abstract Base Entity (Refactored for SOLID)
The project includes an abstract base entity (e.g., `BaseEntity` or domain-specific equivalent) with:
- **Private fields** (encapsulation): `id`, `name`, and shared attributes
- **Getters/setters** for controlled access
- **At least two abstract methods** requiring subclasses to define specific behavior
- **At least one concrete method** shared across all entities
- Polymorphism through overridden methods in subclasses

### Subclasses (LSP-compliant)
At least **two subclasses** extend the abstract base entity:
- Implement the abstract methods
- May introduce unique properties
- Remain substitutable when handled as the base type

### Composition / Aggregation
The model includes at least one “has-a” relationship that is represented:
- In Java as object references
- In PostgreSQL as a foreign key relationship  
  This ensures consistency between the object model and the relational schema.

---

## Advanced Java / OOP Features

### Generics
Generics are used to make repository and utility logic reusable:
- A **generic CRUD repository interface** defines common operations (Create/Read/Update/Delete)
- Domain repositories implement the generic interface for specific entity types

### Lambdas
Lambda expressions are used in at least one functional scenario, such as:
- Sorting lists (Comparator-based sorting)
- Filtering/searching collections
  This demonstrates modern Java functional style while keeping logic readable.

### Reflection / RTTI
A reflection utility demonstrates runtime inspection, including:
- Extracting class name
- Listing fields
- Listing methods
- Optional inspection of annotations (if used)
  The output of this reflection utility is part of the project demonstration and should be referenced in screenshots.

### Interface Default / Static Methods
At least one interface includes:
- a **default method** (shared behavior with opt-in override)
- a **static method** (utility behavior related to the interface)

---

## Exception Handling (Service Layer)
The project maintains the same exception hierarchy and applies it primarily inside the **service layer**:
- `InvalidInputException`
- `DuplicateResourceException` (extends `InvalidInputException`)
- `ResourceNotFoundException`
- `DatabaseOperationException`

**Expected usage:**
- Validation failures raise `InvalidInputException`
- Duplicate constraints or unique conflicts raise `DuplicateResourceException`
- Missing entities raise `ResourceNotFoundException`
- JDBC/SQL failures are wrapped as `DatabaseOperationException`

Controllers should handle exceptions only at a presentation level (display/log), not generate them via business logic.

---

## Database (PostgreSQL + JDBC)
The database is implemented in PostgreSQL and accessed via JDBC.

**Schema requirements covered:**
- At least **two related tables**
- At least **one foreign key** constraint
- Improved constraints where appropriate (NOT NULL, UNIQUE, FK integrity)
- Includes **sample INSERT statements** for test data

The schema is stored in:
- `resources/schema.sql`

---

## Architecture Explanation (Layer Responsibilities)

### Controller Layer
- Delegates all operations to the service layer
- Contains no domain business rules
- Responsible for simple input orchestration (CLI menu or request-style routing)

### Service Layer
- Applies validation rules
- Coordinates operations across repositories
- Implements business workflows (create/update rules, FK checks, etc.)
- Uses repository interfaces (DIP)
- Applies lambdas for sorting/filtering scenarios when appropriate

### Repository Layer
- Implements generic CRUD operations via JDBC
- Maps ResultSets to model objects
- Does not enforce business rules (beyond persistence-level constraints)

### Utilities
- Reflection utility (RTTI demo)
- Sorting utility (lambda demo)
- Optional validators or helpers to support SRP

---

## Repository Structure (Milestone 2)
Expected structure for the refactored SOLID version:

- `src/controller/` — controller layer
- `src/service/` — service layer implementations
- `src/service/interfaces/` — service abstractions
- `src/repository/` — repository implementations (JDBC)
- `src/repository/interfaces/` — repository abstractions (generic CRUD)
- `src/model/` — entities (abstract base + subclasses + composition)
- `src/dto/` — optional DTO layer for clean boundaries
- `src/exception/` — custom exception hierarchy
- `src/utils/` — reflection + sorting utilities
- `src/DatabaseConnection.java` — JDBC connection management
- `src/Main.java` — program entry/demo runner
- `resources/schema.sql` — schema + sample inserts
- `docs/` — UML and screenshots

---

## How to Run

### Requirements
- Java (JDK) installed (recommended: Java 17+)
- PostgreSQL installed and running
- A database created for this project
- JDBC connection credentials configured (host, port, DB name, user, password)

### Setup Steps
1. Create a PostgreSQL database for the project.
2. Run the SQL script in `resources/schema.sql` to create tables and seed sample data.
3. Configure the database connection settings in `DatabaseConnection.java` (or in your chosen configuration approach).
4. Compile the project.
5. Run `Main.java` to execute the driver demonstration.

---

## Demonstration Checklist (What This Project Shows)
This project is designed to demonstrate the following milestone requirements:

### SOLID in action
- Controller depends on a **service interface**
- Service depends on a **repository interface**
- Clear separation of responsibilities across layers

### Advanced OOP usage
- Abstract base + subclasses with **polymorphism**
- Reflection utility output (RTTI)
- Lambda-based sorting/filtering
- Interface default/static methods
- Generic CRUD repository usage

### Functional scenarios
- Create multiple entities
- Update entities
- Validate inputs (and show failure paths)
- Trigger custom exceptions
- Delete with foreign key integrity checks
- Fetch lists with sorting (lambda demonstration)

---

## UML Diagram
An updated UML diagram is included in:
- `docs/uml.png`

It should reflect:
- Abstract base entity and subclasses
- Composition relationships
- Layer boundaries (controller/service/repository)
- Key interfaces (service + repository + utility interfaces as applicable)

---

## Screenshots (Required Evidence)
Screenshots are stored in:
- `docs/screenshots/`

They should include:
- Successful CRUD operations
- Validation failures and exception handling
- Reflection utility output
- Sorted list output demonstrating lambdas

---

## Reflection (Milestone Learning Summary)
This milestone emphasizes improving code quality through SOLID and advanced OOP:
- Better maintainability due to strict layering and SRP
- Easier extension through OCP and polymorphic design
- Safer inheritance through LSP-compliant subclasses
- Cleaner contracts via ISP-based interfaces
- Reduced coupling through DIP and interface-based dependencies

Challenges typically include:
- Identifying and removing business logic from controllers/repositories
- Designing interfaces that stay small but useful
- Mapping composition relationships cleanly in both Java and SQL
- Keeping exception handling consistent in the service layer