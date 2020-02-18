package com.view;

import java.util.LinkedList;

import com.control.CouponControl;
import com.module.Item;
import com.module.coupons.Coupon;
import com.module.coupons.CouponInventory;

public class OutputData {
    //index in the table
    private static final int idIndex = 0;
    private static final int categoryIndex = 1;
    private static final int priceIndexInCart = 1;
    private static final int quantityIndexInCart = 2;
    private static final int priceIndexInReceipt = 2;
    private static final int quantityIndexInReceipt = 3;
    private static final int idColumnWidth = 6;
    private static final int receiptTotalWidth = 30;
    private static final int couponNameTitleWidth = 4;

    public static LinkedList<LinkedList <Object>> tableOfResults(LinkedList<Object> titles, LinkedList <Item> results) {
        LinkedList<LinkedList<Object>> table = new LinkedList<>();
        table.add(titles);
        for (Item result : results) {
            LinkedList<Object> current = result.itemAsList();
            table.add(current);
        }
        return table;
    }

    public static LinkedList<LinkedList <Object>> adjustTableForShoppingCart(LinkedList<LinkedList <Object>> table) {
        if(table.get(0).size() != 0) {//if table is not empty
            //i start from 1, because row 0 is title
            for (int i = 1; i < table.size(); i++) {
                LinkedList<Object> current = table.get(i);
                //delete category
                current.remove(categoryIndex);
                //delete id
                current.remove(idIndex);

                //add total for each
                current.add((double)current.get(priceIndexInCart) * (int)current.get(quantityIndexInCart));
            }
        }
        return table;
    }

    public static LinkedList<LinkedList <Object>> addOrderNumToTable(LinkedList<LinkedList <Object>> table) {
        if(table.get(0).size() != 0) {//if table is not empty
            //i start from 1, because row 0 is title
            for (int i = 1; i < table.size(); i++) {
                LinkedList<Object> current = table.get(i);
                current.add(0, i);
            }
        }
        return table;
    }

    public static LinkedList<LinkedList <Object>> adjustTableForReceipt(LinkedList<LinkedList <Object>> table, LinkedList<LinkedList <Object>> couponInfo) {
        if(table.get(0).size() != 0) {//if table is not empty
            //no titles
            table.removeFirst();
            int originalSize = table.size();

            int rowIndex = 0;
            for (int itemIndex = 0; itemIndex < originalSize; itemIndex++) {
                LinkedList<Object> current = table.get(rowIndex);
                //delete category
                current.remove(categoryIndex);
                //calculate total for each
                double eachTotal = (double)current.get(priceIndexInReceipt) * (int)current.get(quantityIndexInReceipt);
                //add a row to show quantity and total for each kind of items
                LinkedList<Object> rowOfQuantity = new LinkedList<>();
                rowOfQuantity.add("");
                int quantity = (int)current.get(quantityIndexInReceipt);
                String quantityStr = "X " + current.get(quantityIndexInReceipt);
                rowOfQuantity.add(quantityStr);
                //delete quantity from original row
                current.removeLast();
                rowOfQuantity.add(eachTotal);
                //if quantity is bigger than 1, show this row
                if (quantity > 1) {
                    rowIndex++;
                    table.add(rowIndex, rowOfQuantity);

                }
                //add coupon info under each line
                rowIndex++;
                table.add(rowIndex, couponInfo.get(itemIndex));
                rowIndex++;

            }
        }
        return table;
    }

    public static void printTable(LinkedList<LinkedList<Object>> table, boolean symbol) {//if symbol is true then print "|"
        //when empty(empty 2D list will have an empty row)
        if (table == null || table.get(0).size() == 0) {
            return;
        }
        //find column count
        int columnSize = table.get(0).size();
        //find column width
        int[] width = new int[columnSize];
        //find max width

        //for each row
        for (int i = 0; i < table.size(); i++) {
            //in each row, find each column's width and compare to max to find max
            for (int j = 0; j < columnSize; j++) {
                //ignore rows that's empty
                if (table.get(i).size() == 0) {
                    continue;
                }
                Object current = table.get(i).get(j);

                if (current.getClass() == Integer.class) {
                    //if id, consider leading zeros
                    // 2 possibilities: title is id or no titles, which is receipt
                    if (table.get(0).get(0).getClass() != String.class && j == 0) {
                        //receipt
                        width[j] = idColumnWidth;
                    } else if (((String)table.get(0).get(j)).equals("id")) {
                        width[j] = idColumnWidth;//eg: 000012
                    } else {
                        width[j] = Math.max(width[j], Integer.toString((Integer)current).length());
                    }

                } else if (current.getClass() == Double.class) {
                    width[j] = Math.max(width[j], String.format("%.2f", current).length());
                } else if (current.getClass() == String.class) {
                    width[j] = Math.max(width[j], ((String)current).length());
                }
            }
        }

        //print table
        for (int i = 0; i < table.size(); i++) {
            //ignore rows that's empty
            if (table.get(i).size() == 0) {
                continue;
            }
            if (symbol) {
                System.out.print("| ");
            }
            for (int j = 0; j < columnSize; j++) {
                Object current = table.get(i).get(j);
                if (current.getClass() == Integer.class) {
                    //if id, consider leading zeros
                    // 2 possibilities: title is id or no titles, which is receipt
                    if (table.get(0).get(0).getClass() != String.class && j == 0) {
                        //receipt
                        System.out.print(String.format("%0" + width[j] + "d", (Integer)current));
                    } else if (((String)table.get(0).get(j)).equals("id")) {
                        System.out.print(String.format("%0" + width[j] + "d", (Integer)current));
                    } else {
                        System.out.print(String.format("%-" + width[j] + "d", (Integer)current));
                    }
                } else if (current.getClass() == Double.class) {
                    if (!symbol) {
                        int space = receiptTotalWidth - width[0] - width[1] - width[2] - 2;
                        OutputText.multipleChars(space, ' ');
                    }
                    System.out.print(String.format("%" + width[j] + ".2f", current));
                } else if (current.getClass() == String.class) {
                    System.out.print(String.format("%-" + width[j] + "s", current));
                }
                if (symbol) {
                    System.out.print(" | ");
                } else {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }

    }

    public static void showAllCoupons(LinkedList <Coupon> couponStock) {
        //find column width for "name"
        int width = 0;
        for (Coupon coupon : couponStock) {
            width = Math.max(width, coupon.getName().length());
        }
        //compare to column name "name" (4digits)
        width = Math.max(width, couponNameTitleWidth);
        //print first row
        System.out.println("| Code   | " + String.format("%-" + width + "s", "Name") + " |");
        //print coupons
        for (Coupon coupon : couponStock) {
            System.out.println("| " + coupon.getCode() + " | " + String.format("%-" + width + "s", coupon.getName()) + " |");
        }

    }

    public static void showCurrentCoupon(CouponInventory couponInventory) {
        OutputText.showCurrentCouponText();
        if (couponInventory.getCurrentCoupon() == null) {
            OutputText.noCurrentCoupon();
        } else {
            //put currentCoupon into list, because showCoupon() ask for list input
            LinkedList<Coupon> currentCoupon = new LinkedList<>();
            currentCoupon.add(couponInventory.getCurrentCoupon());
            OutputData.showAllCoupons(currentCoupon);
        }
    }

    public static void showCurrentCouponInReceipt(CouponControl couponControl, CouponInventory couponInventory) {
        //coupon description
        if (couponControl.isOverallCanApplyCoupon()) {
            OutputText.multipleChars(receiptTotalWidth, '-');
            System.out.println();
            Coupon current = couponInventory.getCurrentCoupon();
            System.out.println(current.getCode());
            System.out.println(current.getName());
            if (current.getCategory() != null) {
                System.out.print(current.getCategory() + ", ");
            }
        }
    }

    public static void showTotalPrice(double total) {
        OutputText.multipleChars(receiptTotalWidth, '-');
        System.out.println();
        System.out.print("Total");
        int space = receiptTotalWidth - 5 - (String.format("%.2f", total).length());
        OutputText.multipleChars(space, ' ');
        System.out.println(String.format("%.2f", total));
    }
    

    public static void showDiscount(double total, double finalTotal) {
        System.out.print("Discount");
        int space = receiptTotalWidth - 8 - (String.format("%.2f", total).length()) - 1;
        OutputText.multipleChars(space, ' ');
        System.out.println(String.format("%.2f", finalTotal - total));
    }

    public static boolean change(double payment, double total) {
        if (payment >= total) {
            System.out.println("Change");
            System.out.println(String.format("%.2f", payment - total));
            return true;
        } else {
            System.out.println("Insufficient fund");
            return false;
        }
    }
    
}
