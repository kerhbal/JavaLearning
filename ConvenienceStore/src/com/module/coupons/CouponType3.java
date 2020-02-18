package com.module.coupons;

public class CouponType3 extends Coupon{
    //buy $xx get yy free
    private double threshold;
    private int freeItemCount;
    private String freeItemName;

    public double getThreshold() {
        return threshold;
    }


    public int getFreeItemCount() {
        return freeItemCount;
    }


    public String getFreeItemName() {
        return freeItemName;
    }


    public CouponType3(String code, String category, double threshold, int freeItemCount, String freeItemName) {
        this.setCode(code);
        this.setCategory(category);
        this.threshold = threshold;
        this.freeItemCount = freeItemCount;
        this.freeItemName = freeItemName;
        String name = "";
        if (category != null) {
            name += category + " , ";
        }
        name += "Buy $" + String.format("%.0f", threshold) + " Get " + freeItemCount + " " + freeItemName + " free";
        this.setName(name);
    }

}
