package com.module.coupons;

import com.module.Item;

public class CouponType4 extends Coupon{
    //buy xx get yy free
    private int buyCount;
    private int freeCount;

    public int getBuyCount() {
        return buyCount;
    }

    public int getFreeCount() {
        return freeCount;
    }


    public CouponType4(String code, String category, int buyCount, int freeCount) {
        this.setCode(code);
        this.setCategory(category);
        this.buyCount = buyCount;
        this.freeCount = freeCount;
        String name = "";
        if (category != null) {
            name += category + " , ";
        }
        name += "Buy " + buyCount + " Get " + freeCount + " free";
        this.setName(name);
    }


}
