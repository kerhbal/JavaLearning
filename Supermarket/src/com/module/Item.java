package com.module;

import com.module.coupons.*;

import java.util.LinkedList;

public class Item {
    private int id;
    private String category;
    private String name;
    private double price;
    private int quantity;
    private double total;//only used in shopping cart
    private double itemFinalTotal;//total after coupon

    public Item(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {
    }

    //convert a single item to a list to prepare constructing 2d list of items for print
    public LinkedList<Object> itemAsList() {
        LinkedList<Object> itemAsList = new LinkedList<>();
        itemAsList.add(getId());
        itemAsList.add(getCategory());
        itemAsList.add(getName());
        itemAsList.add(getPrice());
        itemAsList.add(getQuantity());

        return itemAsList;
    }

    public double getItemFinalTotal() {
        return itemFinalTotal;
    }

    public void setItemFinalTotal(double itemFinalTotal) {
        this.itemFinalTotal = itemFinalTotal;
    }

    public Item(Item from) {
        //to copy
        this.setId(from.getId());
        this.setName(from.getName());
        this.setCategory(from.getCategory());
        this.setPrice(from.getPrice());
        this.setQuantity(from.getQuantity());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return price * quantity;
    }

    //can apply coupon:


    public boolean possibleToApplyCoupon(CouponInventory couponInventory) {
        //if no current coupon, then pass
        Coupon currentCoupon = couponInventory.getCurrentCoupon();
        if (currentCoupon == null) {
            return false;
        }
        //if not global && if not same category
        if (currentCoupon.getCategory() != null
                && !currentCoupon.getCategory().equalsIgnoreCase(getCategory())) {
            return false;
        }
        return true;
    }

    public boolean canApplyType1(CouponInventory couponInventory) {
        if (couponInventory.getCurrentCoupon().getClass() == CouponType1.class) {
            CouponType1 coupon = (CouponType1) couponInventory.getCurrentCoupon();
            if (getQuantity() >= coupon.getCount()) {
                return true;
            }
        }
        return false;
    }

    public boolean canApplyType2(ShoppingCart shoppingCart, CouponInventory couponInventory) {
        if (couponInventory.getCurrentCoupon().getClass() == CouponType2.class) {
            CouponType2 coupon = (CouponType2) couponInventory.getCurrentCoupon();
            if (coupon.getCategory() == null && shoppingCart.getTotalPrice() >= coupon.getThreshold()) {
                return true;
            }
            if (coupon.getCategory() != null
                    && shoppingCart.getCategoryTotal(coupon.getCategory()) >= coupon.getThreshold()) {
                return true;
            }
        }
        return false;
    }



    public boolean canApplyType3(ShoppingCart shoppingCart, CouponInventory couponInventory) {
        if (couponInventory.getCurrentCoupon().getClass() == CouponType3.class) {
            CouponType3 coupon = (CouponType3) couponInventory.getCurrentCoupon();
            if (coupon.getCategory() == null && shoppingCart.getTotalPrice() >= coupon.getThreshold()) {
                return true;
            }
            if (coupon.getCategory() != null
                    && shoppingCart.getCategoryTotal(coupon.getCategory()) >= coupon.getThreshold()) {
                return true;
            }
        }
        return false;
    }

    public boolean canApplyType4(CouponInventory couponInventory) {
        if (couponInventory.getCurrentCoupon().getClass() == CouponType4.class) {
            CouponType4 coupon = (CouponType4) couponInventory.getCurrentCoupon();
            if (coupon.getCategory().equalsIgnoreCase(getCategory())
                    && getQuantity() >= coupon.getBuyCount()) {
                return true;
            }
        }
        return false;
    }

    public boolean canApplyAnyCoupon(ShoppingCart shoppingCart, CouponInventory couponInventory) {
        return canApplyType1(couponInventory) || canApplyType2(shoppingCart, couponInventory) || canApplyType3(shoppingCart, couponInventory) || canApplyType4(couponInventory);
    }
}
