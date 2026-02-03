package utils;

import model.Card;
import model.Player;
import java.util.List;
import java.util.Comparator;

/**
 * Sorting utility demonstrating lambda expressions
 */
public class SortingUtils {

    /**
     * Sort cards by level (ascending) - using lambda
     */
    public static void sortCardsByLevel(List<Card> cards) {
        cards.sort((c1, c2) -> Integer.compare(c1.getLevel(), c2.getLevel()));
        System.out.println("✅ Cards sorted by level (ascending)");
    }

    /**
     * Sort cards by level (descending) - using lambda
     */
    public static void sortCardsByLevelDesc(List<Card> cards) {
        cards.sort((c1, c2) -> Integer.compare(c2.getLevel(), c1.getLevel()));
        System.out.println("✅ Cards sorted by level (descending)");
    }

    /**
     * Sort cards by name - using lambda
     */
    public static void sortCardsByName(List<Card> cards) {
        cards.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
        System.out.println("✅ Cards sorted by name (alphabetically)");
    }

    /**
     * Sort cards by elixir cost - using method reference
     */
    public static void sortCardsByElixirCost(List<Card> cards) {
        cards.sort(Comparator.comparingInt(Card::getElixirCost));
        System.out.println("✅ Cards sorted by elixir cost");
    }

    /**
     * Filter cards by minimum level - using lambda
     */
    public static List<Card> filterCardsByMinLevel(List<Card> cards, int minLevel) {
        return cards.stream()
                .filter(card -> card.getLevel() >= minLevel)
                .toList();
    }

    /**
     * Sort players by trophies (descending) - using lambda
     */
    public static void sortPlayersByTrophies(List<Player> players) {
        players.sort((p1, p2) -> Integer.compare(p2.getTrophies(), p1.getTrophies()));
        System.out.println("✅ Players sorted by trophies (descending)");
    }

    /**
     * Sort players by level - using lambda
     */
    public static void sortPlayersByLevel(List<Player> players) {
        players.sort(Comparator.comparingInt(Player::getLevel));
        System.out.println("✅ Players sorted by level");
    }

    /**
     * Filter players by minimum trophies - using lambda
     */
    public static List<Player> filterPlayersByMinTrophies(List<Player> players, int minTrophies) {
        return players.stream()
                .filter(player -> player.getTrophies() >= minTrophies)
                .toList();
    }
}