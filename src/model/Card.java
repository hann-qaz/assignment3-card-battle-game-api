package model;
import exception.InvalidInputException;

public abstract class Card extends GameEntity implements Upgradable {
    protected String rarity;
    protected int elixirCost;
    protected int level;

    public Card(int id, String name, String rarity, int elixirCost, int level) {
        super (id, name);
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Card name is empty!");
        }
        if (elixirCost < 1 || elixirCost > 10) {
            throw new InvalidInputException("Elixir cost must be less than 1 and greater than 10!");
        }
        if(level < 1 || level > 16) {
            throw new InvalidInputException("Level must be between 1 and 16!");
        }
    }

    @Override
    public void upgrade() {
        if(canUpgrade() == true) {
            level++;
        }
    }

    @Override
    public boolean canUpgrade() {
        return level < 16;
    }

    //getteri i setteri
    public String getRarity() { return rarity; }
    public int getElixirCost() { return elixirCost; }
    public int getLevel() { return level; }
    public void setRarity( String rarity) { this.rarity = rarity; }
    public void setElixirCost(int elixirCost) { this.elixirCost = elixirCost; }
    public void getLevel(int level) { this.level=level; }

}
