package com.module;

import java.util.LinkedList;

//make this abstract to forbid new object
public class Inventory {
    private LinkedList<Item> stock = new LinkedList <> ();

    public LinkedList<Item> getStock() {
        return stock;
    }

    public void carryIn(Item item, int quantity) {
//        //for shopping cart, first is to copy a new item and set the quantity
//        if (getClass() == ShoppingCart.class) {
//            item = new Item(item);
//            item.setQuantity(quantity);
//        }
        //if there is same item in the stock
        LinkedList<Item> results = searchId(Integer.toString(item.getId()));
        if (results.size() != 0) {
            results.get(0).setQuantity(results.get(0).getQuantity() + quantity);
        } else {
            getStock().add(item);
        }
    }

    public LinkedList <Item> searchId(String command) {
        LinkedList<Item> results = new LinkedList<>();
        for (Item item : getStock()) {
            //search id by temporarily copy a string version of id
            String idString = Integer.toString(item.getId());
            if (idString.equals(command)) {
                //no need to copy item?
                //maybe because item is not node, it's just referenced by the node
                results.add(item);
            }
        }
        return results;
    }

    public LinkedList <Item> searchOrder(String command) {
        LinkedList<Item> results = new LinkedList<>();
        int inputOrder = Integer.parseInt(command);
        if (inputOrder <= getStock().size()) {
            results.add(getStock().get(inputOrder - 1));
        }
        return results;
    }


    public LinkedList <Item> searchName(String command) {
        LinkedList<Item> results = new LinkedList<>();
        for (Item item : getStock()) {
            if (item.getName().contains(command)) {//search name
                results.add(item);
            }
        }
        return results;
    }
}
