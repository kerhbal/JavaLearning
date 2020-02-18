package com;
import java.util.Scanner;
public class GameInputOutput {
    public GameInputOutput() {
    }

    public static void inputName(Player player1, Player player2) {
        System.out.println("Please input player1's nickname");
        Scanner input = new Scanner(System.in);
        String name1 = input.next();
        System.out.println("Please input player2's nickname");
        String name2 = input.next();
        player1.setName(name1);
        player2.setName(name2);
    }

    public static void printBoard(Board board) {
        System.out.print("    ");
        for (int x = 0; x < board.getSize(); x++) {
            System.out.print(String.format("%-3s", x + 1));
        }
        System.out.println();

        for (int y = 0; y < board.getSize(); y++) {
            System.out.print(String.format("%-4s", (char)('A' + y)));
            for (int x = 0; x < board.getSize(); x++) {
                char[] printSymbols = {'.', 'x', 'o'};
                System.out.print(String.format("%-3s", printSymbols[board.getValue(y, x)]));
            }
            System.out.println();
        }
    }

    public static Point inputTarget() {
        Point target = new Point();
        Scanner input = new Scanner(System.in);
        String targetInput = input.next();;
        //early exit
        if (targetInput.length() < 2) {
            return null;
        }
        target.setY(targetInput.charAt(0) - 'A' + 1);
        target.setX(Integer.parseInt(targetInput.substring(1)));
        if (target.getY() > 15 || target.getX() < 1) {
            return null;
        }
        return target;
    }

    public static void showWinner(Player player) {
        System.out.println(player.getName() + " wins!");
    }
}
