package controller;

import dto.CardDTO;
import model.*;
import service.interfaces.CardServiceInterface;
import exception.*;

import java.util.List;

/**
 * CardController - SRP: only handles user interaction, delegates to service
 */
public class CardController {
    private final CardServiceInterface cardService;

    // Constructor injection - DIP
    public CardController(CardServiceInterface cardService) {
        this.cardService = cardService;
    }

    public void createCard(CardDTO dto) {
        try {
            Card card = createCardFromDTO(dto);
            cardService.createCard(card);
        } catch (InvalidInputException | DatabaseException e) {
            System.err.println("❌ Error creating card: " + e.getMessage());
        }
    }

    public void listAllCards() {
        try {
            List<Card> cards = cardService.getAllCards();
            System.out.println("\n📋 All Cards:");
            cards.forEach(card -> System.out.println("  - " + card.getBasicInfo()));
        } catch (DatabaseException e) {
            System.err.println("❌ Error listing cards: " + e.getMessage());
        }
    }

    public void upgradeCard(int id) {
        try {
            cardService.upgradeCard(id);
        } catch (ResourceNotFoundException | DatabaseException | InvalidInputException e) {
            System.err.println("❌ Error upgrading card: " + e.getMessage());
        }
    }

    public void deleteCard(int id) {
        try {
            cardService.deleteCard(id);
        } catch (ResourceNotFoundException | DatabaseException e) {
            System.err.println("❌ Error deleting card: " + e.getMessage());
        }
    }

    private Card createCardFromDTO(CardDTO dto) throws InvalidInputException {
        if ("WARRIOR".equals(dto.type)) {
            return new WarriorCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, dto.damage, dto.hp);
        } else if ("BUILDING".equals(dto.type)) {
            return new BuildingCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, dto.hp, dto.lifetime);
        } else if ("SPELL".equals(dto.type)) {
            return new SpellCard(0, dto.name, dto.rarity, dto.elixirCost, dto.level, dto.radius, dto.damage);
        } else {
            throw new InvalidInputException("❌ Wrong card type. It should be WARRIOR, BUILDING, or SPELL");
        }
    }
}