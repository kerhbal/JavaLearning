package com.control;

import com.module.Item;
import com.module.ShoppingCart;
import com.module.coupons.*;
import com.view.Input;
import com.view.OutputData;
import com.view.OutputText;

import java.util.LinkedList;

public class CouponControl {
    private boolean overallCanApplyCoupon = false;

    public boolean isOverallCanApplyCoupon() {
        return overallCanApplyCoupon;
    }

    public void setOverallCanApplyCoupon(boolean overallCanApplyCoupon) {
        this.overallCanApplyCoupon = overallCanApplyCoupon;
    }

    public void couponUI(CouponInventory couponInventory) {
        while (true) {
            OutputText.showTitleAvailableCoupon();
            OutputData.showAllCoupons(couponInventory.getStock());
            OutputData.showCurrentCoupon(couponInventory);
            OutputText.setCurrent();
            int command = Input.getInputInt();
            if (command == 0) {
                break;
            } else if (command == 1) {
                OutputText.askCode();
                String code = Input.getInputString();
                LinkedList<Coupon> results = couponInventory.searchCode(code);
                if (results.size() == 0) {
                    OutputText.couponNotValid();

                } else {
                    //only 1 current coupon, so every time add coupon first need to remove current
                    couponInventory.removeCurrent();
                    results.get(0).setCurrent(true);
                    OutputText.setCouponSuccess();
                }
                System.out.println();
            }
        }
    }

    public void overallCanApplyCoupon(ShoppingCart shoppingCart, CouponInventory couponInventory) {
        for (int i = 0; i < shoppingCart.getInventory().getStock().size(); i++) {
            Item item = shoppingCart.getInventory().getStock().get(i);
            //if no coupon applied, this is final price
            item.setItemFinalTotal(item.getTotal());
            //if current coupon exists and category fits(or no category requirement)
            if (item.possibleToApplyCoupon(couponInventory)) {
                //if current coupon can be applied
                if (item.canApplyAnyCoupon(shoppingCart, couponInventory)) {
                    setOverallCanApplyCoupon(true);
                    break;
                }
            }
        }
    }

    //output is 2D linked list so that can be added to final table in OutputData
    public LinkedList<LinkedList <Object>> applyCoupon(ShoppingCart shoppingCart, CouponInventory couponInventory) {
        LinkedList<LinkedList <Object>> result = new LinkedList<LinkedList <Object>>();
        //apply coupon below each item
        //if no coupon, this is final price
        for (Item item : shoppingCart.getInventory().getStock()) {
            double priceAfterCoupon = item.getTotal();
            if (item.canApplyType1(couponInventory)) {
                //(n-1) * (quantity/n) * singlePrice + (quantity/n) * singlePrice * discount
                //the former quantity/n need to round up
                //the latter quantity/n need to round down
                CouponType1 coupon = (CouponType1) couponInventory.getCurrentCoupon();
                double n = coupon.getCount();
                priceAfterCoupon =
                        Math.ceil(item.getQuantity() / n) * (n - 1) * item.getPrice() +
                        Math.floor(item.getQuantity() / n) * item.getPrice() * (double)(100 - coupon.getPercentOff()) / 100;
            } else if (item.canApplyType2(shoppingCart, couponInventory)) {
                CouponType2 coupon = (CouponType2) couponInventory.getCurrentCoupon();
                //find all illegible items to distribute save
                //1. tell the whole value of illegible items
                //2. calculate the discount rate, consider multiple rounds of saving
                //3. apply the discount to illegible items
                double illegibleValue = 0;
                if (coupon.getCategory() == null) {
                    illegibleValue = shoppingCart.getTotalPrice();
                } else if (coupon.getCategory().equalsIgnoreCase(item.getCategory())) {
                    illegibleValue = shoppingCart.getCategoryTotal(item.getCategory());
                }
                double totalSave = (int)(illegibleValue / coupon.getThreshold()) * coupon.getSave();
                double discountRate = totalSave / illegibleValue;
                priceAfterCoupon = item.getTotal() * (1 - discountRate);
            } else if (item.canApplyType3(shoppingCart, couponInventory)) {
                //already in the description, here do nothing
            } else if (item.canApplyType4(couponInventory)) {
                CouponType4 coupon = (CouponType4) couponInventory.getCurrentCoupon();
                //this coupon must have a valid category(no null)
                int nonFreeQuantity = item.getQuantity();
                if (coupon.getCategory().equalsIgnoreCase(item.getCategory())) {
                    // nonFreeQuantity = n - n/(buyCount + freeCount)(round down) * freeCount
                    nonFreeQuantity =
                            item.getQuantity() - (int)Math.floor((double)item.getQuantity() / (coupon.getBuyCount()
                                    + coupon.getFreeCount())) * coupon.getFreeCount();
                }
                priceAfterCoupon = item.getPrice() * nonFreeQuantity;
            }

            if (item.canApplyAnyCoupon(shoppingCart, couponInventory)) {
                //fill in the table:
                LinkedList<Object> couponRow = new LinkedList<>();
                couponRow.add("");
                couponRow.add("CP: " + couponInventory.getCurrentCoupon().getCode());
                couponRow.add(priceAfterCoupon);
                result.add(couponRow);
            } else {
                //fill in blank
                LinkedList<Object> blankRow = new LinkedList<>();
                result.add(blankRow);
            }
            item.setItemFinalTotal(priceAfterCoupon);
        }

        return result;
    }
}
