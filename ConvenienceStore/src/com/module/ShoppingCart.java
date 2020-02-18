package com.module;

public class ShoppingCart {
    private Inventory inventory = new Inventory();


    public Inventory getInventory() {
        return inventory;
    }

    public void carryIn(Item item, int quantity) {
        //do not touch original items in warehouse
        item = new Item(item);
        item.setQuantity(quantity);
        inventory.carryIn(item, quantity);
    }

    public void carryInDemo() {
        carryIn(new Item(16, "Watermelon", "fruits", 6.11, 12 ), 12);
        carryIn(new Item(27, "CMA316", "xxx", 2.3, 900 ), 2);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Item item : inventory.getStock()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public double getCategoryTotal(String category) {
        double categoryTotal = 0;
        for (Item item : inventory.getStock()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                categoryTotal += item.getPrice() * item.getQuantity();
            }
        }
        return categoryTotal;
    }

    public double getFinalTotalPrice() {
        //after coupon
        double total = 0;
        for (Item item : inventory.getStock()) {
            total += item.getItemFinalTotal();
        }
        return total;
    }

    public void carryOut(Item item, int out) {
        item.setQuantity(item.getQuantity() - out);
        if (item.getQuantity() == 0) {
            inventory.getStock().remove(item);
        }
    }

    public void clearShoppingCart() {
        inventory.getStock().clear();
    }


}
