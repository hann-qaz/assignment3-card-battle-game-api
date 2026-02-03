package service.interfaces;

import model.Player;
import exception.*;
import java.util.List;

/**
 * Service interface for Player operations
 * Demonstrates: ISP and DIP
 */
public interface PlayerServiceInterface {
    void createPlayer(Player player) throws InvalidInputException, DatabaseException;
    List<Player> getAllPlayers() throws DatabaseException;
    Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseException;
    void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseException;
    void deletePlayer(int id) throws ResourceNotFoundException, DatabaseException;
    void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseException, InvalidInputException;
}