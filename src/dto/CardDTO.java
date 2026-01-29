package dto;

public class CardDTO {
    public String name;
    public String type;
    public String rarity;
    public int elixirCost;
    public int level;
    public int damage;
    public int hp;
    public int radius;
    public int lifetime;

    public CardDTO(String name, String type, String rarity, int elixirCost, int level, int damage, int hp, int radius, int lifetime) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
        this.damage = damage;
        this.hp = hp;
        this.radius = radius;
        this.lifetime = lifetime;
    }
}
