package com.control;

import com.module.Item;
import com.module.ShoppingCart;
import com.module.Warehouse;
import com.view.Input;
import com.view.OutputText;

import java.util.LinkedList;

public class ShoppingCartControl {
    private LinkedList<Object> titles;
    private LinkedList<Object> titlesWithOrder;

    public ShoppingCartControl() {
        this.titles = new LinkedList<Object>();
        titles.add("Name");
        titles.add("Price");
        titles.add("Quantity");
        titles.add("Total");

        this.titlesWithOrder = new LinkedList<Object>();
        titlesWithOrder.add("Order");
        titlesWithOrder.add("Name");
        titlesWithOrder.add("Price");
        titlesWithOrder.add("Quantity");
        titlesWithOrder.add("Total");

    }

    public LinkedList<Object> getTitles() {
        return titles;
    }

    public LinkedList<Object> getTitlesWithOrder() {
        return titlesWithOrder;
    }

    public void carryInControl(Warehouse warehouse, ShoppingCart shoppingCart) {
        while (true) {
            OutputText.askId();
            String idString = Input.getInputString();
            LinkedList<Item> results = warehouse.getInventory().searchId(idString);
            if (results.size() == 0) {
                OutputText.noItem();
                OutputText.askContinue();
                if (Input.getInputInt() == 0) {
                    break;
                }
            } else if (results.size() == 1) {
                OutputText.askQuantityInShoppingCart();
                int quantity = Input.getInputInt();
                //if no enough in warehouse
                if (quantity > results.get(0).getQuantity()) {
                    OutputText.insufficientQuantityWhenAddingToCart();
                    OutputText.askContinue();
                    if (Input.getInputInt() == 0) {
                        break;
                    }
                }
                shoppingCart.carryIn(results.get(0), quantity);
                OutputText.finishAdding();
                break;
            }
        }
    }


    public Item carryOutControl(ShoppingCart shoppingCart) {
        while (true) {
            OutputText.askOrder();
            String inputString = Input.getInputString();
            if (Integer.parseInt(inputString) == 0) {
                //0 is exit when delete from shopping cart
                break;
            }
            LinkedList <Item> results = new LinkedList<>();
            results = shoppingCart.getInventory().searchOrder(inputString);

            if (results.size() == 0) {
                OutputText.noItem();
                OutputText.askContinue();
                if (Input.getInputInt() == 0) {
                    break;
                }
            } else if (results.size() == 1) {
                OutputText.askQuantityOutShoppingCart();

                int out = Input.getInputInt();
                if (out > results.get(0).getQuantity()) {
                    OutputText.insufficientQuantityWhenDelete();
                } else {
                    shoppingCart.carryOut(results.get(0), out);
                    OutputText.deleteFinish();
                    return results.get(0);
                }
                OutputText.askContinue();
                if (Input.getInputInt() == 0) {
                    break;
                }
            }

        }
        return null;
    }
}
