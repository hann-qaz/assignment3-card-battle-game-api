import controller.CardController;
import controller.PlayerController;
import dto.CardDTO;
import dto.PlayerDTO;
import model.*;
import exception.InvalidInputException;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Clash Royale game API");
        System.out.println("==============================================");

        CardController cardController = new CardController();
        PlayerController playerController = new PlayerController();

        // 1. Создание игроков
        System.out.println("\n Creating players:");
        playerController.createPlayer(new PlayerDTO("Noob2012"));
        playerController.createPlayer(new PlayerDTO("scibidi_Pro"));

        // Попытка создать дубликат
        System.out.println("\n Attempting to create duplicate player(exception):");
        playerController.createPlayer(new PlayerDTO("scibidi_Pro"));

        // 2 создаем карты
        System.out.println("\n📝 Creating cards:");
        cardController.createCard(new CardDTO("Knight", "WARRIOR", "COMMON", 3, 14, 267, 2339, 0, 0));
        cardController.createCard(new CardDTO("Fireball", "SPELL", "RARE", 4, 13, 831, 0, 3, 0));
        cardController.createCard(new CardDTO("Princess", "WARRIOR", "LEGENDARY", 3, 12, 185, 286, 0, 0));

        // 3 выводим все карты
        cardController.listAllCards();

        // 4 улучшаем карты
        System.out.println("\n ↑ Upgrading card...");
        cardController.upgradeCard(2);

        // 5 полиморфизм
        System.out.println("\n Demonstrating polymorphism:");
        demonstratePolymorphism();

        // 6 Композиция (Deck) - один объект содержит другой
        System.out.println("\n Demonstrating composition (Deck):");
        demonstrateDeck();

        // 7 Валидация
        System.out.println("\n Demonstrating validation:");
        demonstrateValidation();

        // 8 Список игроков
        System.out.println("\n All Players:");
        playerController.listAllPlayers();

        // 9 Добавляем трофеев
        System.out.println("\n Adding trophies:");
        playerController.addTrophies(1, 30);

        // 10 Удаляем карты
        System.out.println("\n Deleting card:");
        cardController.deleteCard(2);

        cardController.listAllCards();

        System.out.println("\n :-) Demo completed!");
    }

    private static void demonstratePolymorphism() {
        Card[] cards = {
                new WarriorCard(0, "Goblin", "COMMON", 2, 5, 100, 50),
                new SpellCard(0, "Zap", "COMMON", 2, 8, 3, 120)
        };

        for (Card card : cards) {
            System.out.println("  Card: " + card.getName() + " Type: " + card.getType() +
                    " Can upgrade: " + card.canUpgrade());
        }
    }

    private static void demonstrateDeck() {
        try {
            Deck deck = new Deck(1, "My Battle Deck");
            Card card1 = new WarriorCard(1, "Knight", "COMMON", 3, 10, 200, 80);
            Card card2 = new SpellCard(2, "Fireball", "RARE", 4, 9, 5, 150);

            deck.addCard(card1);
            deck.addCard(card2);

            System.out.println("  Deck: " + deck.getDeckName());
            System.out.println("  Total cards: " + deck.getCards().size());
            System.out.println("  Average elixir: " + deck.getAverageElixirCost());
        } catch (InvalidInputException e) {
            System.err.println("  Error: " + e.getMessage());
        }
    }

    private static void demonstrateValidation() {
        try {
            Card invalidCard = new WarriorCard(0, "", "COMMON", 15, -1, 100, 50);
            invalidCard.validate();
        } catch (InvalidInputException e) {
            System.out.println(" :-) Validation caught: " + e.getMessage());
        }
    }
}
