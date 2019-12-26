import java.util.Random;
import java.util.Scanner;

public class Game1024 {
    public static void main(String[] args) {
        new Game1024().run();
    }
    //generate blankBoard
    int size = 4;
    int[][] board = new int[size][size];
    int score = 0;
    int round = 0;
    boolean lose = false;
    public void run() {
        while(true) {
            //random numbers and check lose
            randomNumbers();
            //print board and score with random numbers
            print();
            //if no move possible, lose
            if (lose) {
                System.out.println("LOSE!");
                return;
            }
            //moveNumbers + combine
            moveNumbers();
        }



    }

    public void randomNumbers() {
        generateRandom();
        if (round == 0) {//2 numbers at round 0
            int[] randomDetail = generateRandom();
            checkLose(randomDetail);
        }
    }

    public int[] generateRandom() {
        int randomValue, randomY, randomX;
        int[] randomDetail = new int[2];
        do {//at this point no chance to fall into dead loop
            //there will be lose check at the end of this function
            randomY = new Random().nextInt(size);
            randomX = new Random().nextInt(size);
        } while (board[randomY][randomX] != 0);//occupied
        randomValue = new Random().nextInt(2) + 1;
        board[randomY][randomX] = randomValue;
        randomDetail[0] = randomY;
        randomDetail[1] = randomX;
        return randomDetail;
    }

    public void checkLose(int[] randomDetail) {
        //check lose
        //1. after filling this number there is no extra space
        //2. neighbours from horizontal and vertical are not this number
        int randomY = randomDetail[0];
        int randomX = randomDetail[1];
        boolean noZero = true;
        int test;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size - 1; x++) {
                test = board[y][x] + board[y][x + 1];
                //if there is zero
                if (test == board[y][x] || test == board[y][x + 1]) {
                    noZero = false;
                    break;
                }
            }
            //break outer
            if (!noZero) {
                break;
            }

        }
        if (noZero) {
            //noSameDown = over limit || no same down
            boolean noSameDown = (randomY + 1 >= size) || (board[randomY][randomX] != board[randomY + 1][randomX]);
            boolean noSameUp = (randomY - 1 < 0) || (board[randomY][randomX] != board[randomY - 1][randomX]);
            boolean noSameLeft = (randomX - 1 < 0) || (board[randomY][randomX] != board[randomY][randomX - 1]);
            boolean noSameRight = (randomX + 1 >= size) || (board[randomY][randomX] != board[randomY][randomX + 1]);
            lose = noSameDown && noSameUp && noSameLeft && noSameRight;
            }
        }
    }

    public void print() {
        System.out.println("Score : " + score);
        //find the biggest number to decide format
        int max = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                max = max > board[y][x] ? max : board[y][x];
            }
        }
        //decide format
        int digits = Integer.toString(max).length();
        String[] format = new String[5]; //only use 1234
        for (int i = 0; i < 4; i++) {
            format[i] = "%-" + (digits + 2)  + "s";
        }
        //print with format
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    System.out.print(String.format(format[digits], "."));
                } else {
                    System.out.print(String.format(format[digits], board[i][j]));
                }
            }
            System.out.println();
        }
    }

    public void moveNumbers() {
        //move + combine
        //if next/last one is same or 0, overwrite next/last one, current one change to 0
        int allMoves = 0;
        String move;
        while (allMoves == 0) {
            //if no actual move is made, loop
            System.out.println("Please make your move:");
            Scanner input = new Scanner(System.in);
            move = input.next();;
            if (move.equals("W")) {
                for (int x = 0; x < size; x++) {
                    for (int y = 1; y < size; y++) {
                        for (int check = y; check > 0; check--) {//move until hit top wall or different number
                            if (board[check][x] == 0) {
                                break;
                            }
                            if (board[check - 1][x] == 0) {
                                board[check - 1][x] = board[check][x];
                                board[check][x] = 0;
                                allMoves++;

                            }
                            if (board[check][x] == board[check - 1][x]) {
                                score += board[check][x] * 2;
                                board[check - 1][x] = board[check][x] * 2;
                                board[check][x] = 0;
                                allMoves++;
                            }
                        }
                    }
                }
            }
            if (move.equals("S")) {
                for (int x = 0; x < size; x++) {
                    for (int y = size - 2; y >= 0; y--) {
                        for (int check = y; check < size - 1; check++) {//move until hit top wall or different number
                            if (board[check][x] == 0) {
                                break;
                            }
                            if (board[check + 1][x] == 0) {
                                board[check + 1][x] = board[check][x];
                                board[check][x] = 0;
                                allMoves++;
                            }
                            if (board[check][x] == board[check + 1][x]) {
                                score += board[check][x] * 2;
                                board[check + 1][x] = board[check][x] * 2;
                                board[check][x] = 0;
                                allMoves++;
                            }
                        }
                    }
                }
            }
            if (move.equals("A")) {
                for (int y = 0; y < size; y++) {
                    for (int x = 1; x < size; x++) {
                        for (int check = x; check > 0; check--) {//move until hit top wall or different number
                            if (board[y][check] == 0) {
                                break;
                            }
                            if (board[y][check - 1] == 0) {
                                board[y][check - 1] = board[y][check];
                                board[y][check] = 0;
                                allMoves++;
                            }
                            if (board[y][check] == board[y][check - 1]) {
                                score += board[y][check] * 2;
                                board[y][check - 1] = board[y][check] * 2;
                                board[y][check] = 0;
                                allMoves++;
                            }
                        }
                    }
                }
            }
            if (move.equals("D")) {
                for (int y = 0; y < size; y++) {
                    for (int x = size - 2; x >= 0; x--) {
                        for (int check = x; check < size - 1; check++) {//move until hit top wall or different number
                            if (board[y][check] == 0) {
                                break;
                            }
                            if (board[y][check + 1] == 0) {
                                board[y][check + 1] = board[y][check];
                                board[y][check] = 0;
                                allMoves++;
                            }
                            if (board[y][check] == board[y][check + 1]) {
                                score += board[y][check] * 2;
                                board[y][check + 1] = board[y][check] * 2;
                                board[y][check] = 0;
                                allMoves++;
                            }
                        }
                    }
                }
            }
        }
        round++;
    }
}
