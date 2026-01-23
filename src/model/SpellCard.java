package model;

public class SpellCard extends Card {
    private int radius;
    private int damage;

    public SpellCard(int id, String name, String rarity, int elixirCost, int level, int radius, int damage) {
        super (id, name, rarity, elixirCost, level);
        this.radius = radius;
        this.damage = damage;
    }

    @Override
    public String getType() { return "Spell:"; }

    //getters setters
    public int getRadius() { return radius; }
    public int getDamage() { return damage; }
    public void setRadius(int Radius) {this.radius = radius; }
    public void setDamage() { this.damage = damage;}


}
