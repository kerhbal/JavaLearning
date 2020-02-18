package com.control;

import com.module.Item;
import com.module.ShoppingCart;
import com.module.Warehouse;
import com.module.coupons.CouponInventory;
import com.view.Input;
import com.view.OutputData;
import com.view.OutputText;

import java.util.LinkedList;

public class CheckOutControl {

    public static void checkOut(CouponControl couponControl, ShoppingCart shoppingCart,
                                ShoppingCartControl shoppingCartControl,
                                Warehouse warehouse, CouponInventory couponInventory) {
        OutputText.receiptHead();
        couponControl.overallCanApplyCoupon(shoppingCart, couponInventory);
        LinkedList<LinkedList<Object>> couponTable = couponControl.applyCoupon(shoppingCart, couponInventory);
        LinkedList<LinkedList<Object>> tableBeforeAdjust = OutputData.tableOfResults(
                shoppingCartControl.getTitles(), shoppingCart.getInventory().getStock()
        );
        OutputData.printTable(OutputData.adjustTableForReceipt(tableBeforeAdjust, couponTable), false);
        OutputData.showCurrentCouponInReceipt(couponControl, couponInventory);
        OutputData.showTotalPrice(shoppingCart.getTotalPrice());
        OutputData.showDiscount(shoppingCart.getTotalPrice(), shoppingCart.getFinalTotalPrice());
        OutputData.showTotalPrice(shoppingCart.getFinalTotalPrice());
        buy(couponControl, shoppingCart, warehouse);
    }

    public static void buy(CouponControl couponControl, ShoppingCart shoppingCart, Warehouse warehouse) {
        OutputText.confirmBuy();
        int command = Input.getInputInt();
        //each time whether decide to pay, reset overAllCanApplyCoupon
        couponControl.setOverallCanApplyCoupon(false);
        if (command == 1) {
            if (OutputData.change(OutputText.payment(), shoppingCart.getFinalTotalPrice())) {
                for (Item item : shoppingCart.getInventory().getStock()) {
                    Item remove = warehouse.getInventory().searchId(Integer.toString(item.getId())).get(0);
                    int quantity = item.getQuantity();
                    warehouse.carryOut(remove, quantity);
                }
                shoppingCart.clearShoppingCart();
            }
        }
    }
}
