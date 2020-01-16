package JavaLearning;

import java.util.Scanner;

public class Reversi {
    public static void main(String[] args) {
        new Reversi().run();
    }

    int size = 9;
    char[][] board = new char[size][size];
    //shadowBoard is used to test if target is legal to be a move,
    // and re-use this by pasting to board if legal, avoid double check when actually move
    char[][] shadowBoard = new char[size][size];
    //start from fixed 2
    int blackScore = 2;
    int whiteScore = 2;
    boolean nextIsBlack = true;

    public void run() {
        board = generateBoard();
        //Console.debugPrintln(board);
        game();
    }

    public char[][] generateBoard() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y == 0 && x == 0) {
                    board[y][x] = ' ';
                } else if (y == 0) {
                    board[y][x] = (char)('0' + x);

                } else if (x == 0) {
                    board[y][x] = (char)('A' + y - 1);
                } else {
                    board[y][x] = '.';
                }

            }

        }
        //fixed start
        board[4][4] = 'o';
        board[4][5] = 'x';
        board[5][4] = 'x';
        board[5][5] = 'o';
//        board[2][2] = 'o';
//        board[2][3] = 'x';
//        board[3][2] = 'x';
//        board[3][3] = 'o';
        return board;
    }

    public void game() {
        while(true) {
            showBoard();
            if (findMove() == 1) {//first find if there is any possible move
                //move
                String target;
                int[] targetArray = new int[2];
                while(true) {
                    Scanner input = new Scanner(System.in);
                    target = input.next();
                    targetArray = new int[2];
                    targetArray[0] = target.charAt(0) - 'A' + 1;
                    targetArray[1] = target.charAt(1) - '0';
                    int[] result = canMove(targetArray[0], targetArray[1]);//then find if this target is legal to move
                    if (result[0] == 1) {//if can move
                        //copy shadowBoard to board
                        for (int tempY = 0; tempY < size; tempY++) {
                            for (int tempX = 0; tempX < size; tempX++) {
                                board[tempY][tempX] = shadowBoard[tempY][tempX];
                            }
                        }
                        //read previous score
                        int selfScore = nextIsBlack ? blackScore : whiteScore;
                        int enemyScore = nextIsBlack ? whiteScore : blackScore;
                        //update score
                        for (int i = 1; i < 9; i++) {
                            selfScore += result[i];
                            enemyScore -= result[i];
                        }
                        if (nextIsBlack) {
                            blackScore = selfScore + 1;
                            whiteScore = enemyScore;
                        } else {
                            blackScore = enemyScore;
                            whiteScore = selfScore + 1;
                        }
                        break;
                    } else {
                        //if wrong, send message and re-move
                        System.out.println("illegal move, please try again!");
                    }
                }

            } else if (findMove() == 0) {
                String next = nextIsBlack ? "Black" : "White";
                System.out.println(next + " was skipped");
            } else {//board is full
                String winner = blackScore > whiteScore ? "Black" : "White";
                System.out.println(winner + " wins!");
                break;
            }
            nextIsBlack = !nextIsBlack;
        }
    }

    public void showBoard() {
        System.out.println("Score: " + blackScore + " : " + whiteScore);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(String.format("%-3s", board[y][x]));
            }
            System.out.println();
        }
        String next = nextIsBlack ? "Black" : "White";
        System.out.println("It's " + next + "'s turn");

    }

    public int findMove() {
        //when full, return -1;
        //when no target can move, return 0;
        //when there is possible move, return 1;
        //find if there is possible move, from 1,1
        int spaceCount = 0;
        for (int y = 1; y < size; y++) {
            for (int x = 1; x < size; x++) {
                if (board[y][x] == '.') {
                    spaceCount ++;
                    if (onEdge(y, x)) {
                        if (canMove(y, x)[0] == 1) {//if can move
                                return 1;
                        }
                    }
                }
            }
        }
        if (spaceCount == 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean onEdge(int y, int x) {
        //test
        //1. if this one is not occupied
        //2. if this one is beside a previous move
        boolean isOnEdge = false;
        /*
        w w w
        w p w
        w w w     all w need to be tested
         */
        if (y > 0 && x > 0) {//inside board
            if (board[y][x] == '.') {//this one is not occupied
                //need to prevent index out of bounds
                if ((y + 1 < size && (board[y + 1][x] == 'x' || board[y + 1][x] == 'o'|| board[y + 1][x - 1] == 'x' || board[y + 1][x - 1] == 'o'))
                    ||(x + 1 < size &&( board[y][x + 1] == 'x' || board[y - 1][x + 1] == 'x' || board[y][x + 1] == 'o' || board[y - 1][x + 1] == 'o'))
                    ||((y + 1 < size && x + 1 < size) && (board[y + 1][x + 1] == 'x' || board[y + 1][x + 1] == 'o'))
                    ||board[y - 1][x] == 'x' ||  board[y - 1][x - 1] == 'x' || board[y][x - 1] == 'x' || board[y - 1][x] == 'o' || board[y - 1][x - 1] == 'o'
                        ||  board[y][x - 1] == 'o') {
                    isOnEdge = true;
                }
            }
        }

        return isOnEdge;
    }

    public int[] canMove(int y, int x) {
        //int[] : move[0] is 0/1, means can move or not, move[1] ~ move[8] is how many opponent can be changed
        //move[1] ~ move[8]: up upright right downright down downleft left upleft
        //when testing can move or not, move in the shadowBoard
        int[] move = new int[9];
        //imagine board[y][x] is already finished, than tell if it's legal
        char selfTemp = nextIsBlack ? 'x' : 'o';
        char enemy = nextIsBlack ? 'o' : 'x';
        int enemyCount = 0;//check how many enemies can be turned despite this direction is legal or not
        int realEnemyCount = 0;//how many enemies can be turned actually
        int score = 2;
        //first judge if this location is occupied
        if (board[y][x] != '.') {
            move[0] = 0;
            return move;
        }
        //reset shadowBoard to real board at beginning of each test
        for (int tempY = 0; tempY < size; tempY++) {
            for (int tempX = 0; tempX < size; tempX++) {
                shadowBoard[tempY][tempX] = board[tempY][tempX];
            }
        }
        //up
        int up = 1;
        while(y - up > 0) {
            if (up > 1 && board[y - up][x] == selfTemp) {
                //when there is a same piece at the end of some enemy pieces
                //enemyCount become actual
                realEnemyCount = enemyCount;
                break;
            } else if (board[y - up][x] == enemy) {
                enemyCount++;
            } else { //board[y - up][x] == '.' or hit frame
                up = 1;
                break;
            }
            up++;
        }
        enemyCount = 0; //reset for next direction
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[1] = realEnemyCount;
            realEnemyCount = 0;//reset for next direction
            //write to the shadowBoard
            for (int shadowUp = 0; shadowUp < up; shadowUp++) {
                shadowBoard[y - shadowUp][x] = selfTemp;
            }
        }

        //upRight
        int upRight = 1;
        while(x + upRight < size && y - upRight > 0) {
            if (upRight > 1 && board[y - upRight][x + upRight] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y - upRight][x + upRight] == enemy) {
                enemyCount++;
            } else { //board[y - upRight][x + upRight] == '.' or hit frame
                upRight = 1;
                break;
            }
            upRight++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[2] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowUpRight = 0; shadowUpRight < upRight; shadowUpRight++) {
                shadowBoard[y - shadowUpRight][x + shadowUpRight] = selfTemp;
            }
        }

        //right
        int right = 1;
        while(x + right < size) {
            if (right > 1 && board[y][x + right] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y][x + right] == enemy) {
                enemyCount++;
            } else { //board[y][x + right] == '.'
                right = 1;
                break;
            }
            right++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[3] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowRight = 0; shadowRight < right; shadowRight++) {
                shadowBoard[y][x + shadowRight] = selfTemp;
            }
        }

        //downright
        int downRight = 1;
        while(x + downRight < size && y + downRight < size) {
            if (downRight > 1 && board[y + downRight][x + downRight] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y + downRight][x + downRight] == enemy) {
                enemyCount++;
            } else { //board[y + downRight][x + downRight] == '.'
                downRight = 1;
                break;
            }
            downRight++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[4] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowDownRight = 0; shadowDownRight < downRight; shadowDownRight++) {
                shadowBoard[y + shadowDownRight][x + shadowDownRight] = selfTemp;
            }
        }

        //down
        int down = 1;
        while(y + down < size) {
            if (down > 1 && board[y + down][x] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y + down][x] == enemy) {
                enemyCount++;
            } else { //board[y + down][x] == '.'
                down = 1;
                break;
            }
            down++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[5] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowDown = 0; shadowDown < down; shadowDown++) {
                shadowBoard[y + shadowDown][x] = selfTemp;
            }
        }

        //downLeft
        int downLeft = 1;
        while(x - downLeft > 0 && y + downLeft < size) {
            if (board[y + downLeft][x - downLeft] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y + downLeft][x - downLeft] == enemy) {
                enemyCount++;
            } else { //board[y + downLeft][x - downLeft] == '.' or hit frame
                downLeft = 1;
                break;
            }
            downLeft++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[6] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowDownLeft = 0; shadowDownLeft < downLeft; shadowDownLeft++) {
                shadowBoard[y + shadowDownLeft][x - shadowDownLeft] = selfTemp;
            }
        }

        //left
        int left = 1;
        while(x - left > 0) {
            if (left > 1 && board[y][x - left] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y][x - left] == enemy) {
                enemyCount++;
            } else { //board[y][x - left] == '.' or hit frame
                left = 1;
                break;
            }
            left++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[7] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowLeft = 0; shadowLeft < left; shadowLeft++) {
                shadowBoard[y][x - shadowLeft] = selfTemp;
            }
        }

        //upLeft
        int upLeft = 1;
        while(x - upLeft > 0 && y - upLeft > 0) {
            if (upLeft > 1 && board[y - upLeft][x - upLeft] == selfTemp) {
                realEnemyCount = enemyCount;
                break;
            } else if (board[y - upLeft][x - upLeft] == enemy) {
                enemyCount++;
            } else { //board[y - upLeft][x - upLeft] == '.' or hit frame
                upLeft = 1;
                break;
            }
            upLeft++;
        }
        enemyCount = 0;
        if (realEnemyCount > 0) {
            move[0] = 1;
            move[8] = realEnemyCount;
            realEnemyCount = 0;
            //write to the shadowBoard
            for (int shadowUpLeft = 0; shadowUpLeft < upLeft; shadowUpLeft++) {
                shadowBoard[y - shadowUpLeft][x - shadowUpLeft] = selfTemp;
            }
        }


        return move;
    }
}
