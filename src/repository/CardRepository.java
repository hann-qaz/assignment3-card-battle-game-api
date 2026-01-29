package repository;
import model.*;
import utils.DatabaseConnection;
import exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    public void create(Card card) throws DatabaseException {
        String sql = "insert into cards (name, card_type, rarity, elixir_cost, level, damage, hp, radius, lifetime) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, card.getName());
            stmt.setString(2, card.getType());
            stmt.setString(3, card.getRarity());
            stmt.setInt(4, card.getElixirCost());
            stmt.setInt(5, card.getLevel());
            
            // Set card-specific attributes
            if (card instanceof WarriorCard) {
                WarriorCard warrior = (WarriorCard) card;
                stmt.setInt(6, warrior.getDamage());
                stmt.setInt(7, warrior.getHP());
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
            } else {
                stmt.setInt(6, 0);
                stmt.setInt(7, 0);
                stmt.setInt(8, 0);
                stmt.setInt(9, 0);
            }

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                card.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create card", e);
        }
    }
    public List<Card> getAll() throws DatabaseException {
        List<Card> cards = new ArrayList<>();
        String sql = "select * from cards";

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
    public Card getByID(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "select * from cards where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement (sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCard(rs);
            } else {
                throw new ResourceNotFoundException("Card with id" + id + "not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get card by id", e);
        }
    }

    public void update(int id, Card card) throws DatabaseException, ResourceNotFoundException {
        String sql = "update cards set name = ?, card_type=?, rarity = ?, elixir_cost = ?, level = ?, damage = ?, hp = ?, radius = ?, lifetime = ? where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, card.getName());
            stmt.setString(2, card.getType());
            stmt.setString(3, card.getRarity());
            stmt.setInt(4, card.getElixirCost());
            stmt.setInt(5, card.getLevel());
            
            // Set card-specific attributes
            if (card instanceof WarriorCard) {
                WarriorCard warrior = (WarriorCard) card;
                stmt.setInt(6, warrior.getDamage());
                stmt.setInt(7, warrior.getHP());
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
            } else {
                stmt.setInt(6, 0);
                stmt.setInt(7, 0);
                stmt.setInt(8, 0);
                stmt.setInt(9, 0);
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

    public void delete(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "delete from cards where id = ?";

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
        String type = rs.getString("card_type");
        if ("Warrior".equals(type)) {
            return new WarriorCard(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("rarity"),
                    rs.getInt("elixir_cost"),
                    rs.getInt("level"),
                    rs.getInt("hp"),
                    rs.getInt("damage")
            );
        } else if ("Building".equals(type)) {
            return new BuildingCard(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("rarity"),
                    rs.getInt("elixir_cost"),
                    rs.getInt("level"),
                    rs.getInt("hp"),
                    rs.getInt("lifetime")
            );
        } else {
            return new SpellCard(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("rarity"),
                    rs.getInt("elixir_cost"),
                    rs.getInt("level"),
                    rs.getInt("radius"),
                    rs.getInt("damage")
            );
        }
    }

    public Card getById(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "SELECT * FROM cards WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Card(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rarity"),
                        rs.getInt("elixir_cost"),
                        rs.getInt("level")
                ) {
                    @Override
                    public String getType() {
                        return "";
                    }
                };
            } else {
                throw new ResourceNotFoundException("Card not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get card: ", e);
        }
    }
}