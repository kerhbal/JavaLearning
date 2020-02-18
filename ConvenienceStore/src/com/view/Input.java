package com.view;

import java.util.Scanner;

public class Input {
    public static String getInputString() {
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    public static int getInputInt() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    public static double getInputDouble() {
        Scanner input = new Scanner(System.in);
        return input.nextDouble();
    }
}
