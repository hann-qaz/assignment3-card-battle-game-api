package service.interfaces;

import model.Card;
import exception.*;
import java.util.List;

/**
 * Service interface for Card operations
 * Demonstrates: ISP (focused interface) and DIP (depend on abstraction)
 */
public interface CardServiceInterface {
    void createCard(Card card) throws InvalidInputException, DatabaseException;
    List<Card> getAllCards() throws DatabaseException;
    Card getCardById(int id) throws ResourceNotFoundException, DatabaseException;
    void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException;
    void deleteCard(int id) throws ResourceNotFoundException, DatabaseException;
    void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException;
}