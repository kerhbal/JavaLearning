package com;
public class Play {
    public static void main(String[] args) {
        new Play().run();
    }

    public void run() {
        Game game = new Game();
        int size = 15;
        game.setBoard(new Board(size));
        GameInputOutput.inputName(game.getPlayer1(), game.getPlayer2());
        while (true) {
            GameInputOutput.printBoard(game.getBoard());
            game.askForNext();
            game.move();
            if (game.checkWin()) {
                break;
            }
        }
        game.win();
    }
}
