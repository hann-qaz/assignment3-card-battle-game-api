package repository;

import model.*;
import repository.interfaces.CrudRepository;
import utils.DatabaseConnection;
import exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CardRepository implementing generic CrudRepository
 * Demonstrates: DIP (depends on abstraction), OCP (open for extension)
 */
public class CardRepository implements CrudRepository<Card> {

    @Override
    public void create(Card card) throws DatabaseException {
        String sql = "INSERT INTO cards (name, card_type, rarity, elixir_cost, level, damage, hp, radius, lifetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, card.getName());
            stmt.setString(2, card.getType());
            stmt.setString(3, card.getRarity());
            stmt.setInt(4, card.getElixirCost());
            stmt.setInt(5, card.getLevel());

            // Polymorphism in action - Set card-specific attributes
            if (card instanceof WarriorCard) {
                WarriorCard warrior = (WarriorCard) card;
                stmt.setInt(6, warrior.getDamage());
                stmt.setInt(7, warrior.getHp());
                stmt.setInt(8, 0);
                stmt.setInt(9, 0);
            } else if (card instanceof SpellCard) {
                SpellCard spell = (SpellCard) card;
                stmt.setInt(6, spell.getDamage());
                stmt.setInt(7, 0);
                stmt.setInt(8, spell.getRadius());
                stmt.setInt(9, 0);
            } else if (card instanceof BuildingCard) {
                BuildingCard building = (BuildingCard) card;
                stmt.setInt(6, 0);
                stmt.setInt(7, building.getHp());
                stmt.setInt(8, 0);
                stmt.setInt(9, building.getLifetime());
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new DatabaseException("Failed to create card, no rows affected");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    card.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println(" SQL Error: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("Failed to create card: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Card> getAll() throws DatabaseException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Card card = mapResultSetToCard(rs);
                cards.add(card);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all cards", e);
        }
        return cards;
    }

    @Override
    public Card getById(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "SELECT * FROM cards WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCard(rs);
            } else {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get card by id", e);
        }
    }

    @Override
    public void update(int id, Card card) throws DatabaseException, ResourceNotFoundException {
        String sql = "UPDATE cards SET name = ?, card_type = ?, rarity = ?, elixir_cost = ?, level = ?, damage = ?, hp = ?, radius = ?, lifetime = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, card.getName());
            stmt.setString(2, card.getType());
            stmt.setString(3, card.getRarity());
            stmt.setInt(4, card.getElixirCost());
            stmt.setInt(5, card.getLevel());

            if (card instanceof WarriorCard) {
                WarriorCard warrior = (WarriorCard) card;
                stmt.setInt(6, warrior.getDamage());
                stmt.setInt(7, warrior.getHp());
                stmt.setInt(8, 0);
                stmt.setInt(9, 0);
            } else if (card instanceof SpellCard) {
                SpellCard spell = (SpellCard) card;
                stmt.setInt(6, spell.getDamage());
                stmt.setInt(7, 0);
                stmt.setInt(8, spell.getRadius());
                stmt.setInt(9, 0);
            } else if (card instanceof BuildingCard) {
                BuildingCard building = (BuildingCard) card;
                stmt.setInt(6, 0);
                stmt.setInt(7, building.getHp());
                stmt.setInt(8, 0);
                stmt.setInt(9, building.getLifetime());
            }

            stmt.setInt(10, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update card", e);
        }
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "DELETE FROM cards WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete card", e);
        }
    }

    private Card mapResultSetToCard(ResultSet rs) throws SQLException {
        String cardType = rs.getString("card_type");
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String rarity = rs.getString("rarity");
        int elixirCost = rs.getInt("elixir_cost");
        int level = rs.getInt("level");

        // Polymorphism - creating appropriate subclass based on type
        if ("WARRIOR".equalsIgnoreCase(cardType)) {
            return new WarriorCard(id, name, rarity, elixirCost, level,
                    rs.getInt("hp"), rs.getInt("damage"));
        } else if ("Spell".equalsIgnoreCase(cardType)) {
            return new SpellCard(id, name, rarity, elixirCost, level,
                    rs.getInt("radius"), rs.getInt("damage"));
        } else if ("Building".equalsIgnoreCase(cardType)) {
            return new BuildingCard(id, name, rarity, elixirCost, level,
                    rs.getInt("hp"), rs.getInt("lifetime"));
        }
        throw new SQLException("Unknown card type: " + cardType);
    }
}