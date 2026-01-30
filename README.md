# 🎮 Clash Royale API (JDBC + OOP)

## A. Project Overview
REST-подобное Java приложение для управления сущностями вселенной Clash Royale. Проект демонстрирует принципы ООП, многослойную архитектуру (MVC) и работу с базой данных PostgreSQL через JDBC.

*   **Сущности:** `Player`, `Card` (подклассы: `Warrior`, `Spell`, `Building`), `Deck`.
*   **Отношения:** Один Игрок имеет много Колод; Колода содержит набор Карт (Многие-ко-Многим).

## B. OOP Design Documentation

### 1. Наследование и Абстракция
Базовый абстрактный класс `GameEntity` определяет общие поля (`id`, `name`).
```java
// Abstract Base
public abstract class GameEntity {  }
public abstract class Card extends GameEntity {  }

// Subclasses
public class WarriorCard extends Card { } // +damage, hp
public class Player extends GameEntity {  } // +trophies
```
### 2. Интерфейсы
Validatable: Реализуется в Player и Card для проверки бизнес-правил перед сохранением в БД.
### 3. Композиция
Класс Deck (Колода) содержит коллекцию карт, демонстрируя отношение "has-a".
``
public class Deck {
private List<Card> cards;
}``

### 4. Полиморфизм
Обработка разных типов карт через ссылку на базовый класс:
``
for (Card c : cards) {
    System.out.println(c.getType()); // Выведет "Warrior", "Spell" и т.д.
}``

### 5. UML Hierarchy
```
GameEntity (Abstract)
├── Player (Validatable)
└── Card (Abstract, Validatable, Upgradeable)
    ├── WarriorCard
    ├── SpellCard
    └── BuildingCard
```

### 6. Complete UML Class Diagram
For a comprehensive UML class diagram covering all packages (model, controller, service, repository, dto, exception, utils) with detailed relationships, attributes, methods, and database mappings, see:

**📋 [UML Diagram Generation Prompt](documents/UML_DIAGRAM_PROMPT.md)**

This document contains an AI-ready prompt that you can paste into any AI UML generator (ChatGPT, Claude, PlantUML AI, Mermaid AI) to generate a complete, detailed UML class diagram of the entire project architecture.

## C. Database Description
Используется PostgreSQL. Скрипт находится в  Resources/schema.sql.
Схема и ограничения
players: id (PK), name (Unique), trophies.
cards: id (PK), type, rarity, elixir (Check 1-10).
decks: id (PK), player_id (FK -> players).
deck_cards: Связывает decks и cards. PK составной. position (1-8).
Sample SQL
``
INSERT INTO players (name, level, trophies) VALUES ('Player1', 10, 4500);
INSERT INTO cards (name, card_type, rarity, level) VALUES ('Knight', 'Warrior', 'Common', 5);
``

## D. Controller & CRUD
Контроллеры обрабатывают ввод пользователя и делегируют выполнение бизнес-логике (Service Layer). Основные операции на примере работы с картами:

*   **Create (Создание)**: Метод `createCard` принимает DTO, валидирует данные и создает новую запись в БД.
*   **Read (Чтение)**: Метод `listAllCards` запрашивает все записи из таблицы и выводит их в консоль.
*   **Update (Обновление)**: Метод `upgradeCard` изменяет состояние карты (например, уровень) и сохраняет изменения.

## E. Instructions to Compile and Run
Требуется: JDK 17+, PostgreSQL, JDBC Driver.
Настройка БД: Создайте базу и запустите Resources/schema.sql.
Конфигурация: Укажите свои данные в src/utils/DatabaseConnection.java.
Запуск:
``
# Компиляция
javac -d out -sourcepath src src/Main.java

# Запуск (Windows, разделитель ;)
java -cp "out;lib/postgresql-42.7.2.jar" Main
``
## F. Screenshots (Demo Output)
Демонстрация работы Main.java в консоли:
```
📝 Creating players...
✅ Player created: SuperGamer

❗ Attempting to create duplicate player:
❌ Error: Player with name 'SuperGamer' already exists

📝 Creating cards...
✅ Card created successfully: Knight
✅ Card created successfully: Fireball

⬆️ Upgrading card...
✅ Card upgraded successfully: Knight -> Level 6

🔀 Demonstrating polymorphism:
Card: Goblin | Type: Warrior | Can upgrade: true
Card: Zap | Type: Spell | Can upgrade: true

🃏 Demonstrating composition (Deck):
Deck: My Battle Deck | Total cards: 2 | Avg elixir: 3.5

🗑️ Deleting card...
✅ Card deleted successfully.
```

## G. Reflection
Изучено: Подключение JDBC PreparedStatement, отображение реляционных таблиц на объекты Java, создание иерархии исключений (InvalidInputException, ResourceNotFoundException).
Сложности: Реализация отношения многие-ко-многим (deck_cards) в Java без использования ORM. Правильное закрытие ресурсов Connection через try-with-resources.
Преимущества: Многослойная архитектура (Controller -> Service -> Repo) позволила изолировать SQL логику от бизнес-правил и интерфейса.
