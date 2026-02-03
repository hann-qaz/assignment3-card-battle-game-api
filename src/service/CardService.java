package service;

import model.Card;
import repository.interfaces.CrudRepository;
import service.interfaces.CardServiceInterface;
import exception.*;

import java.util.List;

/**
 * CardService implementing service interface
 * Demonstrates: SRP (only business logic), DIP (depends on interfaces)
 */
public class CardService implements CardServiceInterface {
    private final CrudRepository<Card> cardRepository;

    // Constructor injection - DIP principle
    public CardService(CrudRepository<Card> cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void createCard(Card card) throws InvalidInputException, DatabaseException {
        card.validate(); // SRP: validation is part of business logic
        cardRepository.create(card);
        System.out.println("✅ Card created: " + card.getName());
    }

    @Override
    public List<Card> getAllCards() throws DatabaseException {
        return cardRepository.getAll();
    }

    @Override
    public Card getCardById(int id) throws ResourceNotFoundException, DatabaseException {
        return cardRepository.getById(id);
    }

    @Override
    public void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        card.validate();
        cardRepository.update(id, card);
        System.out.println("✅ Card updated: " + card.getName());
    }

    @Override
    public void deleteCard(int id) throws ResourceNotFoundException, DatabaseException {
        cardRepository.delete(id);
        System.out.println("✅ Card deleted with id: " + id);
    }

    @Override
    public void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        Card card = cardRepository.getById(id);
        if (!card.canUpgrade()) {
            throw new InvalidInputException("Card is already at max level");
        }
        card.upgrade();
        cardRepository.update(id, card);
        System.out.println("✅ Card upgraded: " + card.getName() + " to level " + card.getLevel());
    }
}