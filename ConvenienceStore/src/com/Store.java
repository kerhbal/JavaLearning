package com;

import com.control.*;
import com.module.ShoppingCart;
import com.module.Warehouse;
import com.module.coupons.CouponInventory;
import com.view.Input;
import com.view.OutputData;
import com.view.OutputText;

import java.util.LinkedList;

public class Store {
    public static void main(String[] args) {
        new Store().run(args);
    }

    public void run(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.carryInDemo();
        ShoppingCart shoppingCart = new ShoppingCart();
        CouponInventory couponInventory = new CouponInventory();
        couponInventory.carryInDemo();
        CouponControl couponControl = new CouponControl();
        if (args[0].equals("test")) {
            shoppingCart.carryInDemo();
            couponInventory.getStock().get(1).setCurrent(true);
        }
        WarehouseControl warehouseControl = new WarehouseControl();
        ShoppingCartControl shoppingCartControl = new ShoppingCartControl();
        while (true) {
            OutputText.showMainMenu();
            int mainCommand = Input.getInputInt();
            if (mainCommand == 0) {
                return;
            } else if (mainCommand == 1) {//search and carry in and carry out
                while (true) {
                    OutputText.warehouseManageMenu();
                    int command = Input.getInputInt();
                    if (command == 0) {
                        break;
                    }
                    if (command == 1) {
                        warehouseControl.searchControl(warehouse);
                    } else if (command == 2) {
                        warehouseControl.carryInControl(warehouse);
                    } else if (command == 3) {
                        warehouseControl.carryOutControl(warehouse);
                    }
                }
            } else if (mainCommand == 2) {//sale
                while (true) {
                    OutputText.saleManageMenu();
                    int command = Input.getInputInt();
                    if (command == 0) {
                        break;
                    }
                    if (command == 1) {
                        LinkedList<LinkedList<Object>> tableBeforeAdjust = OutputData.tableOfResults(
                                shoppingCartControl.getTitles(), shoppingCart.getInventory().getStock()
                        );
                        OutputData.printTable(
                                OutputData.adjustTableForShoppingCart(tableBeforeAdjust),
                                true
                        );
                        OutputText.showTotalPrice(shoppingCart.getTotalPrice());
                    } else if (command == 2) {
                        shoppingCartControl.carryInControl(warehouse, shoppingCart);
                    } else if (command == 3) {
                        LinkedList<LinkedList<Object>> tableBeforeAdjust = OutputData.tableOfResults(
                                shoppingCartControl.getTitlesWithOrder(), shoppingCart.getInventory().getStock()
                        );
                        OutputData.printTable(OutputData.addOrderNumToTable(
                                OutputData.adjustTableForShoppingCart(tableBeforeAdjust)),
                                true
                        );
                        shoppingCartControl.carryOutControl(shoppingCart);
                    } else if (command == 4) {
                        couponControl.couponUI(couponInventory);
                    } else if (command == 5) {
                        CheckOutControl.checkOut(couponControl, shoppingCart, shoppingCartControl, warehouse, couponInventory);
                    }
                }
            }
        }
    }
}
