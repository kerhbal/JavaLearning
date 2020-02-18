package com;

public class Board {
    private int size;
    private int[][] boardArray;

    public Board(int size) {
        this.size = size;
        this.boardArray = new int[size][size];
    }

    public Board() {
    }

    public int getSize() {
        return size;
    }

    public void setValue(int y, int x, int value) {
        boardArray[y][x] = value;
    }

    public int getValue(int y, int x) {
        return boardArray[y][x];
    }
}
