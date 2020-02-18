package com.control;

import java.util.LinkedList;

import com.module.Item;
import com.module.Warehouse;
import com.view.Input;
import com.view.OutputData;
import com.view.OutputText;

public class WarehouseControl {
    private LinkedList<Object> titles;

    public WarehouseControl() {
        this.titles = new LinkedList<Object>();
        titles.add("id");
        titles.add("Category");
        titles.add("Name");
        titles.add("Price");
        titles.add("Quantity");

    }

    public Item carryOutControl(Warehouse warehouse) {
        while (true) {
            OutputText.askId();
            String inputString = Input.getInputString();
            LinkedList <Item> results = new LinkedList<>();
            results = warehouse.getInventory().searchId(inputString);
            if (results.size() == 0) {
                OutputText.noItem();
                OutputText.askContinue();
                if (Input.getInputInt() == 0) {
                    break;
                }
            } else if (results.size() == 1) {
                OutputText.askQuantityOutWarehouse();

                int out = Input.getInputInt();
                if (out > results.get(0).getQuantity()) {
                    OutputText.insufficientQuantityWarehouse();
                } else {
                    warehouse.carryOut(results.get(0), out);
                    OutputText.finishCarryOut();
                    OutputData.printTable(OutputData.tableOfResults(titles, results), true);
                }
                OutputText.askContinue();
                if (Input.getInputInt() == 0) {
                    break;
                }
            }

        }
        return null;
    }

    public void carryInControl(Warehouse warehouse) {
        while (true) {
            OutputText.askIfNewItem();
            String ifNewItem = Input.getInputString();
            if (ifNewItem.equals("y")) {
                Item item = new Item();
                OutputText.askName();
                item.setName(Input.getInputString());
                OutputText.askCategory();
                item.setCategory(Input.getInputString());
                OutputText.askPrice();
                item.setPrice(Input.getInputDouble());
                OutputText.askQuantity();
                item.setQuantity(Input.getInputInt());
                int newId = warehouse.getInventory().getStock().size() + 1;
                item.setId(newId);
                //here item is new item, so item.getQuantity() can be quantity
                warehouse.getInventory().carryIn(item, item.getQuantity());
                finishCarryIn(item);
                OutputText.askContinue();
                if (Input.getInputInt() == 0) {
                    break;
                }
            } else if (ifNewItem.equals("n")) {
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
                    OutputText.askQuantityIn();
                    int quantity = Input.getInputInt();
                    warehouse.getInventory().carryIn(results.get(0), quantity);
                    finishCarryIn(results.get(0));
                    OutputText.askContinue();
                    if (Input.getInputInt() == 0) {
                        break;
                    }
                }
            }
        }
    }




    public void searchControl(Warehouse warehouse) {
        while (true) {
            OutputText.askSearch();
            String searchCommand = Input.getInputString();
            OutputData.printTable(OutputData.tableOfResults(titles, warehouse.searchWarehouse(searchCommand)), true);
            OutputText.askIfContinueSearch();
            if (Input.getInputInt() == 0) {
                break;
            }
        }
    }

    public void finishCarryIn(Item item) {
        OutputText.finishCarryIn();
        LinkedList<Item> single = new LinkedList<>();
        single.add(item);
        OutputData.printTable(OutputData.tableOfResults(titles, single), true);
    }
}
