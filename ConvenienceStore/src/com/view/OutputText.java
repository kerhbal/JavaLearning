package com.view;

public class OutputText {
    public static void showMainMenu() {
        System.out.println("1.  Warehouse");
        System.out.println("2.  Store");
        System.out.println("0.  Exit");
    }

    public static void saleManageMenu() {
        System.out.println("1.  Browse shopping cart");
        System.out.println("2.  Add item to shopping cart");
        System.out.println("3.  Remove item from shopping cart");
        System.out.println("4.  Coupons");
        System.out.println("5.  Checkout");
        System.out.println("0.  Return");
    }

    public static void askName() {
        System.out.println("Name");
    }

    public static void askCategory() {
        System.out.println("Category");
    }

    public static void askPrice() {
        System.out.println("Price");
    }

    public static void askQuantity() {
        System.out.println("Quantity");
    }

    public static void askIfNewItem() {
        System.out.println("Is this new item？y/n");
    }

    public static void askQuantityIn() {
        System.out.println("How many to carry in?");
    }

    public static void askQuantityInShoppingCart() {
        System.out.println("How many?");
    }

    public static void askQuantityOutWarehouse() {
        System.out.println("How many to carry out?");
    }

    public static void askQuantityOutShoppingCart() {
        System.out.println("How many to remove?");
    }

    public static void insufficientQuantityWhenDelete() {
        System.out.println("Insufficient quantity in shopping cart");
    }

    public static void insufficientQuantityWhenAddingToCart() {
        System.out.println("Insufficient quantity in store");
    }

    public static void insufficientQuantityWarehouse() {
        System.out.println("Insufficient quantity in warehouse");
    }

    public static void noItem() {
        System.out.println("No such item found");
    }

    public static void askContinue() {
        System.out.println("1. Continue");
        System.out.println("0. Return");
    }

    public static void askId() {
        System.out.println("What's the id of the item?");
    }

    public static void askOrder() {
        System.out.println("What's the order number of the item? input 0 to return");
    }

    public static void multipleChars(int n, char chr) {
        for (int i = 0; i < n; i++) {
            System.out.print(chr);
        }
    }

    public static void finishCarryOut() {
        System.out.println("Finish carrying out");
    }

    public static void warehouseManageMenu() {
        System.out.println("1.  Search");
        System.out.println("2.  Carry In");
        System.out.println("3.  Carry Out");
        System.out.println("0.  Return");
    }

    public static void askSearch() {
        System.out.println("What do you want to search?");
    }

    public static void askIfContinueSearch() {
        System.out.println("1. Search again");
        System.out.println("0. Return");
    }

    public static void deleteFinish() {
        System.out.println("Finish removing");
    }

    public static void finishAdding() {
        System.out.println("Finish adding");
    }

    public static void finishCarryIn() {
        System.out.println("Finish carrying in");
    }

    public static void showTitleAvailableCoupon() {
        System.out.println("Available Coupons");
        OutputText.multipleChars(17, '-');
        System.out.println();
    }

    public static void showTotalPrice(double total) {
        System.out.println("| Total : " + String.format("%.2f", total));
    }

    public static void setCurrent() {
        System.out.println("1. Set current");
        System.out.println("0. Return");
    }

    public static void askCode() {
        System.out.println("Please input coupon code");
    }

    public static void couponNotValid() {
        System.out.println("No such coupon");
    }

    public static void setCouponSuccess() {
        System.out.println("Setting succeed");
    }

    public static void receiptHead() {
        OutputText.multipleChars(30, '-');
        System.out.println();
        System.out.println("          Supermarket");
        OutputText.multipleChars(30, '-');
        System.out.println();
    }

    public static void showCurrentCouponText() {
        System.out.println("Current Coupon");
        OutputText.multipleChars(14, '-');
        System.out.println();
    }

    public static void noCurrentCoupon() {
        System.out.println("None");
    }

    public static double payment() {
        System.out.println("Please input the amount of payment");
        return Input.getInputDouble();
    }

    public static void confirmBuy() {
        System.out.println("1. Confirm checkout");
        System.out.println("0. Return");
    }
}
