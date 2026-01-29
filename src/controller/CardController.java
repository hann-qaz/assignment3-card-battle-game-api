package controller;

import dto.CardDTO;
import model.*;
import service.CardService;
import exception.*;

import java.util.List;

public class CardController {
    private final CardService cardService;

    public CardController() {
        this.cardService = new CardService();
    }

    public void createCard(CardDTO dto) {
        try {
            Card card;
            if ("WARRIOR".equals(dto.type)) {
                card = new WarriorCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, dto.hp, dto.damage);
            } else if ("BUILDING".equals(dto.type)) {
                card = new BuildingCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, dto.hp, dto.lifetime);
            } else {
                card = new SpellCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, dto.radius, dto.damage);
            }
            cardService.createCard(card);
        } catch (InvalidInputException | DatabaseException e) {
            System.err.println("X Error creating card: " + e.getMessage());
        }
    }

    public void listAllCards() {
        try {
            List<Card> cards = cardService.getAllCards();
            System.out.println("\n📋 All Cards:");
            cards.forEach(card -> System.out.println("  - " + card.getBasicInfo()));
        } catch (DatabaseException e) {
            System.err.println("X Error listing cards: " + e.getMessage());
        }
    }

    public void upgradeCard(int id) {
        try {
            cardService.upgradeCard(id);
        } catch (ResourceNotFoundException | DatabaseException | InvalidInputException e) {
            System.err.println("X Error upgrading card: " + e.getMessage());
        }
    }

    public void deleteCard(int id) {
        try {
            cardService.deleteCard(id);
        } catch (ResourceNotFoundException | DatabaseException e) {
            System.err.println("X Error deleting card: " + e.getMessage());
        }
    }
}