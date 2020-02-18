package com.module.coupons;

public class CouponType1 extends Coupon{
    //xx% off
    //second/third/... xx% off
    private int percentOff;
    private int count;


    public int getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(int percentOff) {
        this.percentOff = percentOff;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CouponType1(String code, String category, int count, int percentOff) {
        this.setCode(code);
        this.setCategory(category);
        this.percentOff = percentOff;
        this.count = count;
        String name = "";
        if (category != null) {
            name += category + " , ";
        }
        String countString;
        if (count == 1) {
            countString = "";
        } else if (count == 2) {
            countString = "The 2nd ";
        } else if (count == 3) {
            countString = "The 3rd ";
        } else {
            countString = "The " + count + "th ";
        }
            name += countString + percentOff + "% off";
        this.setName(name);
    }
}
