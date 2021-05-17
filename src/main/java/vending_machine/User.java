package vending_machine;

public class User {

    int id;
    String name;
    String type;

    public User(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}