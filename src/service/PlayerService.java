package service;

import model.Player;
import repository.interfaces.CrudRepository;
import service.interfaces.PlayerServiceInterface;
import exception.*;

import java.util.List;

/**
 * PlayerService implementing service interface
 * Demonstrates: SRP, DIP
 */
public class PlayerService implements PlayerServiceInterface {
    private final CrudRepository<Player> playerRepository;

    // Constructor injection - DIP
    public PlayerService(CrudRepository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void createPlayer(Player player) throws InvalidInputException, DatabaseException {
        playerRepository.create(player);
        System.out.println(" Player created: " + player.getName());
    }

    @Override
    public List<Player> getAllPlayers() throws DatabaseException {
        return playerRepository.getAll();
    }

    @Override
    public Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseException {
        return playerRepository.getById(id);
    }

    @Override
    public void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        player.validate();
        playerRepository.update(id, player);
        System.out.println(" Player updated: " + player.getName());
    }

    @Override
    public void deletePlayer(int id) throws ResourceNotFoundException, DatabaseException {
        playerRepository.delete(id);
        System.out.println(" Player deleted with id: " + id);
    }

    @Override
    public void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        if (trophies < 0) {
            throw new InvalidInputException("Cannot add negative trophies");
        }
        Player player = playerRepository.getById(playerId);
        player.setTrophies(player.getTrophies() + trophies);
        playerRepository.update(playerId, player);
        System.out.println(" Added " + trophies + " trophies to " + player.getName());
    }
}