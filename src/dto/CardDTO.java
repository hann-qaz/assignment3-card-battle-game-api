package dto;

public class CardDTO {
    public String name;
    public String type;
    public String rarity;
    public int elixirCost;
    public int level;

    public CardDTO(String name, String type, String rarity, int elixirCost, int level) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
    }
}
