import java.util.Scanner;


public class ChessGame {
    public static void main(String[] args) {
        new ChessGame().run();
    }
    int size = 16;
    String[][] board = new String[size][size];
    public void run() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please input player1's nickname");
        String name1 = input.next();
        System.out.println("Please input player2's nickname");
        String name2 = input.next();

        game(name1, name2);
    }

    public void game(String player1, String player2) {
        board = generateBoard();
        boolean isPlayer1Next = true;
        String winner;
        while (true) {
            showBoard();
            String who = isPlayer1Next ? player1 : player2;
            System.out.println("It's " + who + "'s turn");

            int[] target = {0, 0};
            //if error or target is occupied
            while (true) {
                target = getTarget();
                if (findError(target)) {
                    System.out.println("错误，请重新落子");
                    continue;
                }
                break;
            }

            isPlayer1Next = !isPlayer1Next;
            board = updateBoard(target, isPlayer1Next);

            winner = checkWin();
            if (winner != null) {
                break;
            }
        }
        showBoard();
        String winnerName = winner.equals("x") ? player1 : player2;
        System.out.println(winnerName + " win!");
    }

    public String[][] generateBoard() {
        String[][] startBoard = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j == 0) {
                    startBoard[i][j] = " ";
                } else if (i == 0) {
                    //int to char...
                    /*
                    startBoard[i][j] = (char)(j + '0');
                    will cause:
                    1  2  3  4  5  6  7  8  9  :  ;  <  =  >  ?
                     */
                    //need to use string
                    startBoard[i][j] = Integer.toString(j);

                } else if (j == 0) {
                    startBoard[i][j] = Character.toString((char)('A' + i - 1));
                } else {
                    startBoard[i][j] = ".";
                }
            }
        }
        return startBoard;
    }

    public void showBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String format = String.format("%-3s", board[i][j]);
                    System.out.print(format);
            }
            System.out.println();

        }
    }

    public int[] getTarget() {
        Scanner input = new Scanner(System.in);
        int[] target = new int[2];
        String myInput = input.next();
        target[0] = myInput.charAt(0) - 'A' + 1;

        if (myInput.length() < 2) {//eg: if A12
            return null;
        }
        target[1] = Integer.parseInt(myInput.substring(1));
        return target;
    }

    public boolean findError(int[] target) {
        boolean error = false;
        if (target[0] < 1 || target[0] > size - 1) {
            error =  true;
        } else if (target[1] < 1 || target[1] > size - 1) {
            error = true;
        } else if (!board[target[0]][target[1]].equals(".")) {
            error = true;
        }

        if (error) {
            System.out.println("Wrong move, please try again!");
            return true;
        } else {
            return false;
        }

    }

    public String[][] updateBoard(int[] target, boolean isPlayer1Next) {
        board[target[0]][target[1]] = !isPlayer1Next ? "x" : "o";
        return board;
    }

    public String checkWin() {
        int count = 0;
        //horizontal
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (!board[i][j].equals(".")) {
                    if (board[i][j].equals(board[i][j - 1])) {
                        count++;
                        if (count == 4) {
                            return board[i][j];
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        //vertical
        for (int j = 1; j < size; j++) {
            for (int i = 1; i < size; i++) {
                if (!board[i][j].equals(".")) {
                    if (board[i][j].equals(board[i - 1][j])) {
                        count++;
                        if (count == 4) {
                            return board[i][j];
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        /*
        here x means where to look up
        x  .  .    .  x  .
        .  x  . -> .  .  x
        .  .  x    .  .  .
        i: [1, size - 4)
        j: [1, size - 1]
        k: [i, size)
         */
        for (int i = 1; i < size - 4; i++) {
            for (int j = 1, k = i; j <=  size - i  && k < size; j++, k++) {
                if (!board[j][k].equals(".")) {
                    if (board[j][k].equals(board[j - 1][k - 1])) {
                        count++;
                        if (count == 4) {
                            return board[j][k];
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        /*
        here x means where to look up
        x  .  .    .  .  .
        .  x  . -> x  .  .
        .  .  x    .  x  .
         */
        for (int i = 1; i < size - 4; i++) {
            for (int j = i, k = 1; j < size && k <= size - i; j++, k++) {
                if (!board[j][k].equals(".")) {
                    if (board[j][k].equals(board[j - 1][k - 1])) {
                        count++;
                        if (count == 4) {
                            return board[j][k];
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }

        /*
        here x means where to look up
        .  x  .    .  .  x
        x  .  . -> .  x  .
        .  .  .    x  .  .
         */
        for (int i = 5; i < size; i++) {
            for (int j = 1, k = i; j <= i && k >= 1; j++, k--) {
                if (!board[j][k].equals(".")) {
                    if (board[j][k].equals(board[j - 1][k + 1])) {
                        count++;
                        if (count == 4) {
                            return board[j][k];
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        /*
        here x means where to look up
        .  .  x    .  .  .
        .  x  . -> .  .  x
        x  .  .    .  x  .
         */
        for (int i = 1; i < size - 5; i++) {
            for (int j = size - 1, k = i; j >= i && k < size; j--, k++) {
                if (!board[i][j].equals(".")) {
                    if (board[i][j].equals(board[i - 1][j - 1])) {
                        count++;
                        if (count == 4) {
                            return board[i][j];
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }

        return null;
    }
}
