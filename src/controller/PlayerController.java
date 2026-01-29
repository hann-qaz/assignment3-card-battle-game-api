package controller;

import dto.PlayerDTO;
import model.Player;
import service.PlayerService;
import exception.*;

import java.util.List;

public class PlayerController {
    private final PlayerService playerService;

    public PlayerController() {
        this.playerService = new PlayerService();
    }

    public void createPlayer(PlayerDTO dto) {
        try {
            Player player = new Player(0, dto.name, 1, 0);
            playerService.createPlayer(player);
        } catch (InvalidInputException | DatabaseException | DuplicateResourceException e) {
            System.err.println("X Error creating player: " + e.getMessage());
        }
    }

    public void listAllPlayers() {
        try {
            List<Player> players = playerService.getAllPlayers();
            players.forEach(player -> System.out.println(player.getBasicInfo() +
                    " Level: " + player.getLevel() + " Trophies: " + player.getTrophies()));
        } catch (DatabaseException e) {
            System.err.println("X Error listing players: " + e.getMessage());
        }
    }

    public void addTrophies(int playerId, int trophies) {
        try {
            playerService.addTrophies(playerId, trophies);
        } catch (ResourceNotFoundException | DatabaseException | InvalidInputException e) {
            System.err.println("X Error adding trophies: " + e.getMessage());
        }
    }
}
