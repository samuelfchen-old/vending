package vending_machine.snacks;

public class Drinks implements Snack {

    private String category = "drink";
    private String name;
    private double price;
    private int quantity;

    public Drinks(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
