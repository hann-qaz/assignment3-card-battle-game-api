import controller.CardController;
import controller.PlayerController;
import dto.CardDTO;
import dto.PlayerDTO;
import model.*;
import repository.CardRepository;
import repository.PlayerRepository;
import repository.interfaces.CrudRepository;
import service.CardService;
import service.PlayerService;
import service.interfaces.CardServiceInterface;
import service.interfaces.PlayerServiceInterface;
import utils.ReflectionUtils;
import utils.SortingUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🎮 Clash Royale Game API - Assignment 4 (SOLID)");
        System.out.println("=".repeat(60));

        // Dependency Injection - DIP in action
        CrudRepository<Card> cardRepo = new CardRepository();
        CrudRepository<Player> playerRepo = new PlayerRepository();

        CardServiceInterface cardService = new CardService(cardRepo);
        PlayerServiceInterface playerService = new PlayerService(playerRepo);

        CardController cardController = new CardController(cardService);
        PlayerController playerController = new PlayerController(playerService);

        // 1. Creating players
        System.out.println("\n👤 Creating players:");
        playerController.createPlayer(new PlayerDTO("Noob2012"));
        playerController.createPlayer(new PlayerDTO("scibidi_Pro"));
        playerController.createPlayer(new PlayerDTO("ProGamer123"));

        // 2. Creating cards
        System.out.println("\n📝 Creating cards:");
        cardController.createCard(new CardDTO("Knight", "WARRIOR", "COMMON", 3, 14, 267, 2339, 0, 0));
        cardController.createCard(new CardDTO("Fireball", "SPELL", "RARE", 4, 13, 831, 0, 3, 0));
        cardController.createCard(new CardDTO("Princess", "WARRIOR", "LEGENDARY", 3, 12, 185, 286, 0, 0));
        cardController.createCard(new CardDTO("Cannon", "BUILDING", "COMMON", 3, 11, 0, 824, 0, 30));
        cardController.createCard(new CardDTO("Giant", "WARRIOR", "RARE", 5, 10, 211, 3275, 0, 0));

        // 3. List all cards
        cardController.listAllCards();

        // 4. Polymorphism demonstration
        System.out.println("\n🔄 Demonstrating POLYMORPHISM (LSP):");
        demonstratePolymorphism();

        // 5. Composition demonstration
        System.out.println("\n🗂️ Demonstrating COMPOSITION (Deck):");
        demonstrateDeck();

        // 6. LAMBDAS: Sorting cards
        System.out.println("\n🔀 Demonstrating LAMBDAS (Sorting):");
        try {
            List<Card> cards = cardService.getAllCards();

            System.out.println("\n📊 Original order:");
            cards.forEach(c -> System.out.println("  " + c.getBasicInfo()));

            SortingUtils.sortCardsByLevel(cards);
            System.out.println("After sorting by level:");
            cards.forEach(c -> System.out.println("  " + c.getBasicInfo()));

            SortingUtils.sortCardsByName(cards);
            System.out.println("\nAfter sorting by name:");
            cards.forEach(c -> System.out.println("  " + c.getBasicInfo()));

            // Filter using lambda
            System.out.println("\n🔍 Cards with level >= 12:");
            List<Card> filteredCards = SortingUtils.filterCardsByMinLevel(cards, 12);
            filteredCards.forEach(c -> System.out.println("  " + c.getBasicInfo()));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 7. REFLECTION: Inspect objects
        System.out.println("\n🔬 Demonstrating REFLECTION (RTTI):");
        try {
            Card knightCard = new WarriorCard(1, "TestKnight", "COMMON", 3, 10, 200, 80);
            ReflectionUtils.inspectClass(knightCard);

            Player testPlayer = new Player(1, "ReflectionTest", 5, 1000);
            ReflectionUtils.inspectClass(testPlayer);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 8. GENERICS: Using generic repository methods
        System.out.println("\n📦 Demonstrating GENERICS (Generic Repository):");
        try {
            // Using default method from generic interface
            int cardCount = cardRepo.count();
            System.out.println("Total cards in repository: " + cardCount);

            int playerCount = playerRepo.count();
            System.out.println("Total players in repository: " + playerCount);

            // Using static method from interface
            boolean valid = CrudRepository.isValidId(5);
            System.out.println("Is ID 5 valid? " + valid);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 9. INTERFACE default/static methods
        System.out.println("\n🔌 Demonstrating INTERFACE default/static methods:");
        try {
            Card card = cardService.getAllCards().get(0);
            // Using Printable interface default method
            card.printDetails();

            // Using Printable interface static method
            String header = Printable.formatHeader("Card Details");
            System.out.println(header);
            System.out.println(card.toFormattedString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 10. Upgrade cards
        System.out.println("\n⬆️ Upgrading cards:");
        cardController.upgradeCard(1);
        cardController.upgradeCard(2);

        // 11. Add trophies
        System.out.println("\n🏆 Adding trophies:");
        playerController.addTrophies(1, 50);
        playerController.addTrophies(2, 100);

        // 12. List all players
        playerController.listAllPlayers();

        // 13. Sorting players
        System.out.println("\n📊 Demonstrating LAMBDAS with Players:");
        try {
            List<Player> players = playerService.getAllPlayers();
            SortingUtils.sortPlayersByTrophies(players);
            players.forEach(p -> System.out.println("  " + p.getBasicInfo() +
                    " | Trophies: " + p.getTrophies()));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 14. Delete card
        System.out.println("\n🗑️ Deleting card:");
        cardController.deleteCard(3);

        System.out.println("\n✅ Demo completed!");
        System.out.println("=".repeat(60));
    }

    private static void demonstratePolymorphism() {
        // LSP - Liskov Substitution Principle
        Card[] cards = {
                new WarriorCard(0, "Goblin", "COMMON", 2, 5, 100, 50),
                new SpellCard(0, "Zap", "COMMON", 2, 8, 3, 120),
                new BuildingCard(0, "Tesla", "COMMON", 4, 9, 540, 35)
        };

        for (Card card : cards) {
            System.out.println("  📌 Card: " + card.getName() +
                    " | Type: " + card.getType() +
                    " | Can upgrade: " + card.canUpgrade());
            // Polymorphism - each card type behaves correctly
            card.printDetails(); // Using Printable interface
        }
    }

    private static void demonstrateDeck() {
        try {
            Deck deck = new Deck(1, "My Battle Deck");
            Card card1 = new WarriorCard(1, "Knight", "COMMON", 3, 10, 200, 80);
            Card card2 = new SpellCard(2, "Fireball", "RARE", 4, 9, 3, 500);
            Card card3 = new BuildingCard(3, "Cannon", "COMMON", 3, 8, 400, 30);

            deck.addCard(card1);
            deck.addCard(card2);
            deck.addCard(card3);

            System.out.println("  Deck: " + deck.getDeckName());
            System.out.println("  Cards in deck: " + deck.getCards().size());
            System.out.println("  Average elixir cost: " + deck.getAverageElixirCost());
        } catch (Exception e) {
            System.err.println("  Error: " + e.getMessage());
        }
    }
}