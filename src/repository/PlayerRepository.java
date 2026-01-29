package repository;

import model.Player;
import utils.DatabaseConnection;
import exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {

    public void create(Player player) throws DatabaseException, DuplicateResourceException {
        String sql = "INSERT INTO players (name, level, trophies) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getLevel());
            stmt.setInt(3, player.getTrophies());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                player.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new DuplicateResourceException("Player with name '" + player.getName() + "' already exists");
            }
            throw new DatabaseException("Failed to create player", e);
        }
    }

    public List<Player> getAll() throws DatabaseException {
        List<Player> players = new ArrayList<>();
        String sql = "select * from players";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                players.add(mapResultSetToPlayer(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all players", e);
        }

        return players;
    }

    public Player getById(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "select * from players where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPlayer(rs);
            } else {
                throw new ResourceNotFoundException("Player with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get player by id", e);
        }
    }

    public void update(int id, Player player) throws DatabaseException, ResourceNotFoundException {
        String sql = "update players set name = ?, level = ?, trophies = ? where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getLevel());
            stmt.setInt(3, player.getTrophies());
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Player with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update player", e);
        }
    }

    public void delete(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "delete from players where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Player with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete player", e);
        }
    }

    private Player mapResultSetToPlayer(ResultSet rs) throws SQLException {
        return new Player(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("level"),
                rs.getInt("trophies")
        );
    }
}