package model;

public abstract class GameEntity implements Validatable {
    protected int id;
    protected String name;

    public GameEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract String getType();

    public String getBasicInfo() {
        return "ID: " + id + ", Name: " + name + ", Type: " + getType();
    }

    //getteri
    public int getId() { return id; }
    public String getName() { return name; }

    //setteri
    public void setId(int id) { this.id = id; }
    public void setName (String name) { this.name = name; }
}
