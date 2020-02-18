package com.module.coupons;

import com.module.Item;

import java.util.LinkedList;

public class CouponInventory {
    private LinkedList<Coupon> stock = new LinkedList <> ();
    public LinkedList<Coupon> getStock() {
        return stock;
    }
    public void carryOut(Item item, int out) {

    }

    public void carryIn(Coupon coupon) {
        getStock().add(coupon);
    }

    public void carryInDemo() {
        carryIn(new CouponType1("BLACKF", null, 1, 50));
        carryIn(new CouponType1("BOXING", null, 3, 50));
        carryIn(new CouponType1("HAPPYE", "Fruits", 3, 50));
        carryIn(new CouponType4("FRUITB", "Fruits", 2, 1));
        carryIn(new CouponType3("1GRAPE", "Fruits", 100, 1, "grape"));
        carryIn(new CouponType2("50--20", null, 50, 20));
    }

    public LinkedList <Coupon> searchCode(String command) {
        LinkedList<Coupon> results = new LinkedList<>();
        for (Coupon coupon : getStock()) {
            if (coupon.getCode().equals(command)) {//search name
                results.add(coupon);
            }
        }
        return results;
    }

    public Coupon getCurrentCoupon() {
        for (Coupon coupon : stock) {
            if (coupon.isCurrent()) {
                return coupon;
            }
        }
        return null;
    }

    public void removeCurrent() {
        for (Coupon coupon : stock) {
            coupon.setCurrent(false);
        }
    }
}
