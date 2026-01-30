# UML Class Diagram Generation Prompt

## Instructions for AI UML Generators
Copy the prompt below and paste it into an AI-powered UML diagram generator (such as ChatGPT, Claude, PlantUML AI, or Mermaid AI) to generate a complete UML class diagram for this Card Battle Game API project.

---

## UML Generation Prompt

Create a comprehensive UML class diagram for a Java Card Battle Game API with the following specifications:

### 1. MODEL PACKAGE - Class Hierarchy

**Abstract Base Class: GameEntity**
- Attributes:
  - `# id: int`
  - `# name: String`
- Methods:
  - `+ GameEntity(id: int, name: String)`
  - `+ {abstract} getType(): String`
  - `+ getBasicInfo(): String`
  - `+ getId(): int`
  - `+ getName(): String`
  - `+ setId(id: int): void`
  - `+ setName(name: String): void`
- Implements: `Validatable`
- Database: Base for entities with ID and name

**Abstract Class: Card (extends GameEntity)**
- Attributes:
  - `# rarity: String`
  - `# elixirCost: int`
  - `# level: int`
- Methods:
  - `+ Card(id: int, name: String, rarity: String, elixirCost: int, level: int)`
  - `+ validate(): void {throws InvalidInputException}`
  - `+ upgrade(): void`
  - `+ canUpgrade(): boolean`
  - `+ getRarity(): String`
  - `+ getElixirCost(): int`
  - `+ getLevel(): int`
  - `+ setRarity(rarity: String): void`
  - `+ setElixirCost(elixirCost: int): void`
  - `+ setLevel(level: int): void`
- Implements: `Upgradable`
- Database Table: `cards` (id, name, card_type, rarity, elixir_cost, level, damage, hp, radius, lifetime)

**Concrete Class: WarriorCard (extends Card)**
- Attributes:
  - `- hp: int`
  - `- damage: int`
- Methods:
  - `+ WarriorCard(id: int, name: String, rarity: String, elixirCost: int, level: int, hp: int, damage: int)`
  - `+ getType(): String` *(returns "WARRIOR")*
  - `+ getBasicInfo(): String`
  - `+ getHp(): int`
  - `+ getDamage(): int`
  - `+ setHp(hp: int): void`
  - `+ setDamage(damage: int): void`
- Database: Stored in `cards` table with `card_type = 'Warrior'`

**Concrete Class: SpellCard (extends Card)**
- Attributes:
  - `- radius: int`
  - `- damage: int`
- Methods:
  - `+ SpellCard(id: int, name: String, rarity: String, elixirCost: int, level: int, radius: int, damage: int)`
  - `+ getType(): String` *(returns "Spell")*
  - `+ getBasicInfo(): String`
  - `+ getRadius(): int`
  - `+ getDamage(): int`
  - `+ setRadius(radius: int): void`
  - `+ setDamage(damage: int): void`
- Database: Stored in `cards` table with `card_type = 'Spell'`

**Concrete Class: BuildingCard (extends Card)**
- Attributes:
  - `- hp: int`
  - `- lifetime: int`
- Methods:
  - `+ BuildingCard(id: int, name: String, rarity: String, elixirCost: int, level: int, hp: int, lifetime: int)`
  - `+ getType(): String` *(returns "Building")*
  - `+ getBasicInfo(): String`
  - `+ getHp(): int`
  - `+ getLifetime(): int`
  - `+ setHp(hp: int): void`
  - `+ setLifetime(lifetime: int): void`
- Database: Stored in `cards` table with `card_type = 'Building'`

**Concrete Class: Player (extends GameEntity)**
- Attributes:
  - `- level: int`
  - `- trophies: int`
  - `- deck: Deck`
- Methods:
  - `+ Player(id: int, name: String, level: int, trophies: int)`
  - `+ getType(): String` *(returns "PLAYER")*
  - `+ validate(): void {throws InvalidInputException}`
  - `+ getLevel(): int`
  - `+ getTrophies(): int`
  - `+ getDeck(): Deck`
  - `+ setLevel(level: int): void`
  - `+ setTrophies(trophies: int): void`
  - `+ setDeck(deck: Deck): void`
- Database Table: `players` (id, name, level, trophies)
- Relationship: Composition with Deck (1 to 1)

**Composition Class: Deck**
- Attributes:
  - `- id: int`
  - `- deckName: String`
  - `- cards: List<Card>`
  - `- {static} MAX_CARDS: int = 8`
- Methods:
  - `+ Deck(id: int, deckName: String)`
  - `+ addCard(card: Card): void {throws InvalidInputException}`
  - `+ removeCard(card: Card): void {throws InvalidInputException}`
  - `+ getTotalElixirCost(): int`
  - `+ getAverageElixirCost(): double`
  - `+ getId(): int`
  - `+ setId(id: int): void`
  - `+ getDeckName(): String`
  - `+ setDeckName(deckName: String): void`
  - `+ getCards(): List<Card>`
- Database Table: `decks` (id, player_id, deck_name)
- Join Table: `deck_cards` (deck_id, card_id, position) - Many-to-Many with Cards
- Relationship: Aggregates Cards (max 8)

### 2. MODEL PACKAGE - Interfaces

**Interface: Validatable**
- Methods:
  - `+ validate(): void {throws InvalidInputException}`
- Implemented by: `GameEntity` (and thus all subclasses)

**Interface: Upgradable**
- Methods:
  - `+ upgrade(): void`
  - `+ canUpgrade(): boolean`
- Implemented by: `Card` (and thus all card subclasses)

### 3. CONTROLLER PACKAGE

**Class: CardController**
- Attributes:
  - `- cardService: CardService {final}`
- Methods:
  - `+ CardController()`
  - `+ createCard(dto: CardDTO): void`
  - `+ listAllCards(): void`
  - `+ upgradeCard(id: int): void`
  - `+ deleteCard(id: int): void`
- Dependencies: Uses `CardService`, `CardDTO`
- Pattern: MVC Controller layer

**Class: PlayerController**
- Attributes:
  - `- playerService: PlayerService {final}`
- Methods:
  - `+ PlayerController()`
  - `+ createPlayer(dto: PlayerDTO): void`
  - `+ listAllPlayers(): void`
  - `+ updatePlayerTrophies(id: int, trophies: int): void`
  - `+ deletePlayer(id: int): void`
- Dependencies: Uses `PlayerService`, `PlayerDTO`
- Pattern: MVC Controller layer

### 4. SERVICE PACKAGE

**Class: CardService**
- Attributes:
  - `- cardRepository: CardRepository {final}`
- Methods:
  - `+ CardService()`
  - `+ createCard(card: Card): void {throws InvalidInputException, DatabaseException}`
  - `+ getAllCards(): List<Card> {throws DatabaseException}`
  - `+ getCardByID(id: int): Card {throws ResourceNotFoundException, DatabaseException}`
  - `+ updateCard(id: int, card: Card): void {throws InvalidInputException, ResourceNotFoundException, DatabaseException}`
  - `+ deleteCard(id: int): void {throws ResourceNotFoundException, DatabaseException}`
  - `+ upgradeCard(id: int): void {throws ResourceNotFoundException, DatabaseException, InvalidInputException}`
- Dependencies: Uses `CardRepository`, validates with `Card.validate()`
- Pattern: Business logic layer

**Class: PlayerService**
- Attributes:
  - `- playerRepository: PlayerRepository {final}`
- Methods:
  - `+ PlayerService()`
  - `+ createPlayer(player: Player): void {throws InvalidInputException, DatabaseException, DuplicateResourceException}`
  - `+ getAllPlayers(): List<Player> {throws DatabaseException}`
  - `+ getPlayerByID(id: int): Player {throws ResourceNotFoundException, DatabaseException}`
  - `+ updatePlayer(id: int, player: Player): void {throws InvalidInputException, ResourceNotFoundException, DatabaseException}`
  - `+ deletePlayer(id: int): void {throws ResourceNotFoundException, DatabaseException}`
- Dependencies: Uses `PlayerRepository`, validates with `Player.validate()`
- Pattern: Business logic layer

### 5. REPOSITORY PACKAGE

**Class: CardRepository**
- Methods:
  - `+ create(card: Card): void {throws DatabaseException}`
  - `+ getAll(): List<Card> {throws DatabaseException}`
  - `+ getByID(id: int): Card {throws ResourceNotFoundException, DatabaseException}`
  - `+ update(id: int, card: Card): void {throws DatabaseException, ResourceNotFoundException}`
  - `+ delete(id: int): void {throws ResourceNotFoundException, DatabaseException}`
  - `- mapResultSetToCard(rs: ResultSet): Card {throws SQLException}`
- Database Operations: CRUD on `cards` table
- Polymorphic Mapping: Uses instanceof to map card_type to WarriorCard, SpellCard, or BuildingCard
- Dependencies: Uses `DatabaseConnection`

**Class: PlayerRepository**
- Methods:
  - `+ create(player: Player): void {throws DatabaseException, DuplicateResourceException}`
  - `+ getAll(): List<Player> {throws DatabaseException}`
  - `+ getByID(id: int): Player {throws ResourceNotFoundException, DatabaseException}`
  - `+ update(id: int, player: Player): void {throws DatabaseException, ResourceNotFoundException}`
  - `+ delete(id: int): void {throws ResourceNotFoundException, DatabaseException}`
  - `- mapResultSetToPlayer(rs: ResultSet): Player {throws SQLException}`
- Database Operations: CRUD on `players` table
- Dependencies: Uses `DatabaseConnection`

### 6. DTO PACKAGE

**Class: CardDTO**
- Attributes:
  - `+ name: String`
  - `+ type: String`
  - `+ rarity: String`
  - `+ elixirCost: int`
  - `+ level: int`
  - `+ damage: int`
  - `+ hp: int`
  - `+ radius: int`
  - `+ lifetime: int`
- Purpose: Data Transfer Object for card creation

**Class: PlayerDTO**
- Attributes:
  - `+ name: String`
- Purpose: Data Transfer Object for player creation

### 7. EXCEPTION PACKAGE (All extend RuntimeException)

**Class: InvalidInputException**
- Purpose: Validation failures in model objects
- Used by: `Validatable.validate()` implementations

**Class: DatabaseException**
- Purpose: Database operation errors (wraps SQLException)
- Used by: All Repository methods

**Class: ResourceNotFoundException**
- Purpose: Entity not found by ID
- Used by: Repository getByID, update, delete methods

**Class: DuplicateResourceException**
- Purpose: Duplicate player name violation
- Used by: `PlayerRepository.create()`

**Class: CardUpgradeException**
- Purpose: Card upgrade failures (defined but currently unused)

**Class: InvalidDeckException**
- Purpose: Deck constraint violations (defined but currently unused)

### 8. UTILS PACKAGE

**Class: DatabaseConnection**
- Methods:
  - `+ {static} getConnection(): Connection {throws SQLException}`
- Purpose: PostgreSQL JDBC connection management
- Database: Connects to PostgreSQL database

### 9. RELATIONSHIP SPECIFICATIONS

**Inheritance Relationships:**
- `GameEntity` ← `Card` (abstract inheritance)
- `GameEntity` ← `Player` (concrete inheritance)
- `Card` ← `WarriorCard` (concrete inheritance)
- `Card` ← `SpellCard` (concrete inheritance)
- `Card` ← `BuildingCard` (concrete inheritance)

**Interface Implementation:**
- `GameEntity` implements `Validatable`
- `Card` implements `Upgradable`

**Composition/Aggregation:**
- `Player` contains `Deck` (composition 1:1)
- `Deck` aggregates `List<Card>` (aggregation 1:many, max 8)

**Dependencies (uses relationships):**
- `CardController` → `CardService` → `CardRepository`
- `PlayerController` → `PlayerService` → `PlayerRepository`
- Controllers use DTOs (`CardDTO`, `PlayerDTO`)
- Services use Model classes (`Card`, `Player`)
- Repositories use `DatabaseConnection`
- All layers throw custom exceptions

**Database Relationships:**
- `players` table ← Foreign Key from `decks.player_id`
- `decks` and `cards` tables: Many-to-Many via `deck_cards` join table
- Single Table Inheritance: All card types in `cards` table with `card_type` discriminator

### 10. ARCHITECTURAL PATTERN

This project follows a **Layered MVC Architecture**:
1. **Presentation Layer**: Controllers handle user input
2. **Business Logic Layer**: Services contain validation and business rules
3. **Data Access Layer**: Repositories handle SQL/JDBC operations
4. **Model Layer**: Domain entities with OOP principles
5. **DTO Layer**: Data transfer between layers

### Notes for UML Diagram:
- Show all inheritance with hollow triangle arrows
- Show interface implementation with dashed lines and hollow triangle arrows
- Show composition (Player-Deck) with filled diamond
- Show aggregation (Deck-Cards) with hollow diamond
- Show dependencies with dashed arrows
- Group classes by package with labeled boundaries
- Indicate abstract classes with italicized names or {abstract}
- Show multiplicity on associations (1, *, 1..8, etc.)
- Include database table names as notes on entity classes
- Highlight the MVC pattern flow with different colors if possible

---

## Expected Output
The generated UML diagram should clearly show:
- Complete class hierarchy with GameEntity at the root
- All interfaces and their implementations
- Controller → Service → Repository dependency chain
- Player ←composition→ Deck ←aggregation→ Cards relationships
- All key attributes and methods for each class
- Package organization (model, controller, service, repository, dto, exception, utils)
- Database entity mappings indicated on relevant classes
