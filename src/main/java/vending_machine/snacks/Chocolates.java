package vending_machine.snacks;

public class Chocolates implements Snack {

    private String category = "chocolate";
    private String name;
    private double price;
    private int quantity;

    public Chocolates(String name, double price, int quantity) {
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
