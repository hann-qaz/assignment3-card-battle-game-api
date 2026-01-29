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
            if ("Warrior".equals(dto.type)) {
                int hp = (dto.hp != null) ? dto.hp : 100;
                int damage = (dto.damage != null) ? dto.damage : 50;
                card = new WarriorCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, hp, damage);
            } else if ("Building".equals(dto.type)) {
                int hp = (dto.hp != null) ? dto.hp : 500;
                int lifetime = (dto.lifetime != null) ? dto.lifetime : 30;
                card = new BuildingCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, hp, lifetime);
            } else {
                int radius = (dto.radius != null) ? dto.radius : 3;
                int damage = (dto.damage != null) ? dto.damage : 100;
                card = new SpellCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, radius, damage);
            }
            cardService.createCard(card);
        } catch (InvalidInputException | DatabaseOperationException e) {
            System.err.println("❌ Error creating card: " + e.getMessage());
        }
    }

    public void listAllCards() {
        try {
            List<Card> cards = cardService.getAllCards();
            System.out.println("\n📋 All Cards:");
            cards.forEach(card -> System.out.println("  - " + card.getBasicInfo()));
        } catch (DatabaseOperationException e) {
            System.err.println("X Error listing cards: " + e.getMessage());
        }
    }

    public void upgradeCard(int id) {
        try {
            cardService.upgradeCard(id);
        } catch (ResourceNotFoundException | DatabaseOperationException | InvalidInputException e) {
            System.err.println("X Error upgrading card: " + e.getMessage());
        }
    }

    public void deleteCard(int id) {
        try {
            cardService.deleteCard(id);
        } catch (ResourceNotFoundException | DatabaseOperationException e) {
            System.err.println("X Error deleting card: " + e.getMessage());
        }
    }
}