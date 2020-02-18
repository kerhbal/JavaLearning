package com.module.coupons;


public class CouponType2 extends Coupon{
    //buy $xx save $yy
    private double threshold;
    private double save;

    public double getThreshold() {
        return threshold;
    }

    public double getSave() {
        return save;
    }

    public CouponType2(String code, String category, double threshold, double save) {
        this.setCode(code);
        this.setCategory(category);
        this.threshold = threshold;
        this.save = save;
        String name = "";
        if (category != null) {
            name += category + " , ";
        }
        name += "Buy $" + String.format("%.0f", threshold) + " Save $" + String.format("%.0f", save);
        this.setName(name);
    }
}
