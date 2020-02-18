package com.module;

import java.util.LinkedList;

public class Warehouse {
    private Inventory inventory = new Inventory();

    public Warehouse() {
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void carryInDemo() {
        inventory.carryIn(new Item(12, "APC16", "xxx", 12.62, 1 ), 1);
        inventory.carryIn(new Item(16, "Watermelon", "fruits", 6.11, 12 ), 12);
        inventory.carryIn(new Item(27, "CMA316", "xxx", 2.3, 900 ), 900);
    }

    public LinkedList <Item> searchWarehouse(String command) {
        LinkedList<Item> results = new LinkedList<>();
        results.addAll(inventory.searchId(command));
        results.addAll(inventory.searchName(command));
        return results;
    }

    public void carryOut(Item item, int out) {
        //careful, this will directly touch the item.
        //the item can be existing in both warehouse
        //and shopping cart, so this belongs to com.module.Warehouse()
        //and only used in warehouse
        item.setQuantity(item.getQuantity() - out);
    }
}
