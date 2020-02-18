package com;
public class Game {
    private Board board;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private boolean isPlayer1Next = true;
    private Point target = new Point();

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Game() {

    }

    public boolean isPlayer1Next() {
        return isPlayer1Next;
    }

    public void askForNext() {
        String who = this.isPlayer1Next() ? this.getPlayer1().getName() : this.getPlayer2().getName();
        System.out.println("It's " + who + "'s turn");
    }

    public void move() {
        //if error or target is occupied
        while (true) {
            target = GameInputOutput.inputTarget();
            if (findError(target)) {
                System.out.println("Error, please try again!");
                continue;
            }
            break;
        }
        isPlayer1Next = !isPlayer1Next;
        //updateBoard

        int value = !isPlayer1Next ? 1 : 2;
        board.setValue(target.getY() - 1, target.getX() - 1, value);
    }

    public boolean findError(Point target) {
        if (target == null) {
            return true;
        }

        if (target.getY() < 1 || target.getY() > board.getSize()) {
            return true;
        } else if (target.getX() < 1 || target.getX() > board.getSize()) {
            return true;
        } else if (board.getValue(target.getY() - 1, target.getX() - 1) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkWin() {
        int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}};
        for (int i = 0; i < directions.length; i++) {
            int[] direction = directions[i];
            if(overFiveByDirection(target.getY(), target.getX(), direction[1], direction[0])) {
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
        int reference = board.getValue(y - 1, x - 1);
        while(true) {
            y += dy;
            x += dx;
            if (!(y > 0 && x > 0 && y <= board.getSize() && x <= board.getSize())) {
                break;
            }
            if (board.getValue(y - 1, x - 1) != reference) {
                break;
            }
            count++;

        }
        return count;
    }

    public void win() {
        //if win
        Player whoMoved = isPlayer1Next() ? player2 : player1;
        GameInputOutput.printBoard(board);
        GameInputOutput.showWinner(whoMoved);
    }
}
