package service;

import model.Card;
import repository.CardRepository;
import exception.*;

import java.util.List;

public class CardService {
    private final CardRepository cardRepository;

    public CardService() {
        this.cardRepository = new CardRepository();
    }

    public void createCard(Card card) throws InvalidInputException, DatabaseOperationException {
        card.validate();
        cardRepository.create(card);
        System.out.println("=) Card created: " + card.getName());
    }

    public List<Card> getAllCards() throws DatabaseOperationException {
        return cardRepository.getAll();
    }

    public Card getCardById(int id) throws ResourceNotFoundException, DatabaseOperationException {
        return cardRepository.getById(id);
    }

    public void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {
        card.validate();
        cardRepository.update(id, card);
        System.out.println(";) Card updated: " + card.getName());
    }

    public void deleteCard(int id) throws ResourceNotFoundException, DatabaseOperationException {
        cardRepository.delete(id);
        System.out.println(":) Card deleted with id: " + id);
    }

    public void upgradeCard(int id) throws ResourceNotFoundException, DatabaseOperationException, InvalidInputException {
        Card card = cardRepository.getById(id);
        if (!card.canUpgrade()) {
            throw new InvalidInputException("Card is already at max level");
        }
        card.upgrade();
        cardRepository.update(id, card);
        System.out.println("=) Card upgraded: " + card.getName() + " to level " + card.getLevel());
    }
}
