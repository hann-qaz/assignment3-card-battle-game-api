package controller;

import dto.PlayerDTO;
import model.Player;
import service.interfaces.PlayerServiceInterface;
import exception.*;

import java.util.List;

/**
 * PlayerController - demonstrates SRP
 */
public class PlayerController {
    private final PlayerServiceInterface playerService;

    public PlayerController(PlayerServiceInterface playerService) {
        this.playerService = playerService;
    }

    public void createPlayer(PlayerDTO dto) {
        try {
            Player player = new Player(0, dto.name, 1, 0);
            playerService.createPlayer(player);
        } catch (InvalidInputException | DatabaseException e) {
            System.err.println("❌ Error creating player: " + e.getMessage());
        }
    }

    public void listAllPlayers() {
        try {
            List<Player> players = playerService.getAllPlayers();
            System.out.println("\n👥 All Players:");
            players.forEach(player -> System.out.println("  - " + player.getBasicInfo() +
                    " | Level: " + player.getLevel() + " | Trophies: " + player.getTrophies()));
        } catch (DatabaseException e) {
            System.err.println("❌ Error listing players: " + e.getMessage());
        }
    }

    public void addTrophies(int playerId, int trophies) {
        try {
            playerService.addTrophies(playerId, trophies);
        } catch (ResourceNotFoundException | DatabaseException | InvalidInputException e) {
            System.err.println("❌ Error adding trophies: " + e.getMessage());
        }
    }
}