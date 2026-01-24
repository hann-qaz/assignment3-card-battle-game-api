package model;

import exception.InvalidInputException;

public class Player extends GameEntity {
    private int level;
    private int trophies;
    private Deck deck;

    public Player(int id, String name, int level, int trophies) {
        super(id, name);
        this.level = level;
        this.trophies = trophies;
    }

    @Override
    public String getType() {
        return "PLAYER";
    }

    @Override
    public void validate() throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Player name cannot be empty");
        }
        if (level < 1) {
            throw new InvalidInputException("Player level must be at least 1");
        }
        if (trophies < 0) {
            throw new InvalidInputException("Trophies cannot be negative");
        }
    }

    // Getters and Setters
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getTrophies() { return trophies; }
    public void setTrophies(int trophies) { this.trophies = trophies; }
    public Deck getDeck() { return deck; }
    public void setDeck(Deck deck) { this.deck = deck; }
}