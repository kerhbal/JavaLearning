package JavaLearning;
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
                    System.out.println("Error, please try again!");
                    continue;
                }
                break;
            }

            isPlayer1Next = !isPlayer1Next;
            board = updateBoard(target, isPlayer1Next);
            String whoMoved = isPlayer1Next ? player2 : player1;
            winner = checkWinV2(target) ? whoMoved : null;

            if (winner != null) {
                break;
            }
        }
        showBoard();
        System.out.println(winner + " wins!");
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

        if (myInput.length() < 2) {
            return null;
        }

        target[0] = myInput.charAt(0) - 'A' + 1;


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


    public boolean checkWinV2(int[] target) {
        int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}};
        for (int i = 0; i < directions.length; i++) {
            int[] direction = directions[i];
            if(overFiveByDirection(target[0], target[1], direction[1], direction[0])) {
                return true;
            }
        }
        return false;
    }

    public boolean overFiveByDirection(int targetY, int targetX, int dy, int dx) {
        int count = 0;
        count += countOnDirection(targetY, targetX, dy, dx);
        count += countOnDirection(targetY, targetX, -dy, -dx);
        count --;
        if (count >= 5) {
            return true;
        } else {
            return false;
        }
    }

    public int countOnDirection(int y, int x, int dy, int dx) {
        int count = 1;
        String reference = board[y][x];
        while(true) {
            y += dy;
            x += dx;
            if (!(y > 0 && x > 0 && y < size && x < size)) {
                break;
            }
            if (board[y][x].equals(reference)) {
                count++;
            }
            else {
                break;
            }
        }
        return count;
    }
}
