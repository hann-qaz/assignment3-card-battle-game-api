package repository;
import model.*;
import utils.DatabaseConnection;
import exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    public void create(Card card) throws DatabaseOperationException {
    String sql = "insert into cards (name, card_type, rarity, elixir_cost, level, hp, damage, lifetime, radius) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, card.getName());
        stmt.setString(2, card.getType());
        stmt.setString(3, card.getRarity());
        stmt.setInt(4, card.getElixirCost());
        stmt.setInt(5, card.getLevel());
        
        // Set card-specific attributes based on card type
        if (card instanceof WarriorCard) {
            WarriorCard warrior = (WarriorCard) card;
            stmt.setInt(6, warrior.getHP());
            stmt.setInt(7, warrior.getDamage());
            stmt.setNull(8, Types.INTEGER);
            stmt.setNull(9, Types.INTEGER);
        } else if (card instanceof BuildingCard) {
            BuildingCard building = (BuildingCard) card;
            stmt.setInt(6, building.getHp());
            stmt.setNull(7, Types.INTEGER);
            stmt.setInt(8, building.getLifetime());
            stmt.setNull(9, Types.INTEGER);
        } else if (card instanceof SpellCard) {
            SpellCard spell = (SpellCard) card;
            stmt.setNull(6, Types.INTEGER);
            stmt.setInt(7, spell.getDamage());
            stmt.setNull(8, Types.INTEGER);
            stmt.setInt(9, spell.getRadius());
        }

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            card.setId(rs.getInt(1));
        }
    } catch (SQLException e) {
        throw new DatabaseOperationException("Failed to create card", e);
    }
}
 public List<Card> getAll() throws DatabaseOperationException {
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
            throw new DatabaseOperationException("Failed to get all cards", e);
        }
        return cards;
 }
public Card getByID(int id) throws ResourceNotFoundException, DatabaseOperationException {
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
            throw new DatabaseOperationException("Failed to get card by id", e);
        }
    }

    public void delete(int id) throws ResourceNotFoundException, DatabaseOperationException {
        String sql = "delete from cards where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete card", e);
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
}





















