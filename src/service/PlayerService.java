package service;

import model.Player;
import repository.PlayerRepository;
import exception.*;

import java.util.List;

public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService() {
        this.playerRepository = new PlayerRepository();
    }

    public void createPlayer(Player player) throws InvalidInputException, DatabaseOperationException, DuplicateResourceException {
        player.validate();
        playerRepository.create(player);
        System.out.println("✅ Player created: " + player.getName());
    }

    public List<Player> getAllPlayers() throws DatabaseOperationException {
        return playerRepository.getAll();
    }

    public Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseOperationException {
        return playerRepository.getById(id);
    }

    public void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {
        player.validate();
        playerRepository.update(id, player);
        System.out.println("✅ Player updated: " + player.getName());
    }

    public void deletePlayer(int id) throws ResourceNotFoundException, DatabaseOperationException {
        playerRepository.delete(id);
        System.out.println("✅ Player deleted with id: " + id);
    }

    public void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseOperationException, InvalidInputException {
        if (trophies < 0) {
            throw new InvalidInputException("Cannot add negative trophies");
        }
        Player player = playerRepository.getById(playerId);
        player.setTrophies(player.getTrophies() + trophies);
        playerRepository.update(playerId, player);
        System.out.println("✅ Added " + trophies + " trophies to " + player.getName());
    }
}
