package dto;

public class CardDTO {
    public String name;
    public String type;
    public String rarity;
    public int elixirCost;
    public int level;
    public Integer hp;
    public Integer damage;
    public Integer lifetime;
    public Integer radius;

    public CardDTO(String name, String type, String rarity, int elixirCost, int level) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
    }
    
    public CardDTO(String name, String type, String rarity, int elixirCost, int level, Integer hp, Integer damage, Integer lifetime, Integer radius) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
        this.hp = hp;
        this.damage = damage;
        this.lifetime = lifetime;
        this.radius = radius;
    }
}
