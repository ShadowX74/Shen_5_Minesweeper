/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.Random;

/**
 *
 * @author ShadowX
 */
public class Minefield {

    /*Nodes in the connect four board*/
    Node a1, a2, a3, a4, a5, a6, a7, a8, a9;
    Node b1, b2, b3, b4, b5, b6, b7, b8, b9;
    Node c1, c2, c3, c4, c5, c6, c7, c8, c9;
    Node d1, d2, d3, d4, d5, d6, d7, d8, d9;
    Node e1, e2, e3, e4, e5, e6, e7, e8, e9;
    Node f1, f2, f3, f4, f5, f6, f7, f8, f9;
    Node g1, g2, g3, g4, g5, g6, g7, g8, g9;
    Node h1, h2, h3, h4, h5, h6, h7, h8, h9;
    Node i1, i2, i3, i4, i5, i6, i7, i8, i9;
    int flags = 10;
    
    //2d array to reference nodes easier
    final Node[][] board = {{a1, a2, a3, a4, a5, a6, a7, a8, a9},
    {b1, b2, b3, b4, b5, b6, b7, b8, b9},
    {c1, c2, c3, c4, c5, c6, c7, c8, c9},
    {d1, d2, d3, d4, d5, d6, d7, d8, d9},
    {e1, e2, e3, e4, e5, e6, e7, e8, e9},
    {f1, f2, f3, f4, f5, f6, f7, f8, f9},
    {g1, g2, g3, g4, g5, g6, g7, g8, g9},
    {h1, h2, h3, h4, h5, h6, h7, h8, h9},
    {i1, i2, i3, i4, i5, i6, i7, i8, i9}};
    //height and width of board
    int h = 9, w = 9;

    public Minefield() {
        initBoard();

        setMines(10);
        setNumbers();
    }

    private void initBoard() {
        /*initializes all nodes*/
        for (int i = 0; i < h; i++) { //For each row
            for (int j = 0; j < w; j++) { //For each column
                board[i][j] = new Node();
            }
        }
        /*Sets up links between nodes*/
        for (int i = 0; i < h; i++) { //For each row
            for (int j = 0; j < w; j++) { //For each column

                boolean up = false, down = false, left = false, right = false;

                if (i < h - 1) { //if not bottom
                    down = true;
                    board[i][j].setDown(board[i + 1][j]);
                }
                if (j < w - 1) { //if not right
                    right = true;
                    board[i][j].setRight(board[i][j + 1]);
                }
                if (i != 0) { //if not top
                    up = true;
                    board[i][j].setUp(board[i - 1][j]);
                }
                if (j != 0) { //if not left
                    left = true;
                    board[i][j].setLeft(board[i][j - 1]);
                }
                if (up && left) {
                    board[i][j].setUpleft(board[i - 1][j - 1]);
                }
                if (up && right) {
                    board[i][j].setUpright(board[i - 1][j + 1]);
                }
                if (down && left) {
                    board[i][j].setDownleft(board[i + 1][j - 1]);
                }
                if (down && right) {
                    board[i][j].setDownright(board[i + 1][j + 1]);
                }

            }
        }
    }

    private void setMines(int mines) {
        int counter = 0;
        while (counter < mines) {
            Random rand = new Random();
            int row = rand.nextInt(h);
            int col = rand.nextInt(w);
            if (!board[row][col].isMined()) {
                board[row][col].setMined(true);
                counter++;
            }
        }
    }

    private void setNumbers() {
        for (Node[] row : board) { //Iterating through all the nodes
            for (Node n : row) {
                int counter = 0;
                if (n.getUp() != null) {
                    if (n.getUp().isMined()) {
                        counter++;
                    }
                }
                if (n.getDown() != null) {
                    if (n.getDown().isMined()) {
                        counter++;
                    }
                }
                if (n.getRight() != null) {
                    if (n.getRight().isMined()) {
                        counter++;
                    }
                }
                if (n.getLeft() != null) {
                    if (n.getLeft().isMined()) {
                        counter++;
                    }
                }
                if (n.getUpleft() != null) {
                    if (n.getUpleft().isMined()) {
                        counter++;
                    }
                }
                if (n.getUpright() != null) {
                    if (n.getUpright().isMined()) {
                        counter++;
                    }
                }
                if (n.getDownleft() != null) {
                    if (n.getDownleft().isMined()) {
                        counter++;
                    }
                }
                if (n.getDownright() != null) {
                    if (n.getDownright().isMined()) {
                        counter++;
                    }
                }

                n.setValue(counter);
            }
        }
    }

    public void detectZeroes(int y, int x) { //Keep track of x and y
        if (!(x < 0 || x > w - 1 || y < 0 || y > h - 1)) {
            if (board[y][x].getValue() == 0 && !board[y][x].isFlipped()) {
                board[y][x].setFlipped(true);
                //board[y][x].flagged = false;
                detectZeroes(y + 1, x);
                detectZeroes(y + 1, x + 1);
                detectZeroes(y + 1, x - 1);
                detectZeroes(y, x + 1);
                detectZeroes(y, x - 1);
                detectZeroes(y - 1, x);
                detectZeroes(y - 1, x + 1);
                detectZeroes(y - 1, x - 1);
                if (board[y][x].getUp() != null) {
                    board[y][x].getUp().setFlipped(true);
                }
                if (board[y][x].getUpleft() != null) {
                    board[y][x].getUpleft().setFlipped(true);
                }
                if (board[y][x].getUpright() != null) {
                    board[y][x].getUpright().setFlipped(true);
                }
                if (board[y][x].getDown() != null) {
                    board[y][x].getDown().setFlipped(true);
                }
                if (board[y][x].getDownleft() != null) {
                    board[y][x].getDownleft().setFlipped(true);
                }
                if (board[y][x].getDownright() != null) {
                    board[y][x].getDownright().setFlipped(true);
                }
                if (board[y][x].getRight() != null) {
                    board[y][x].getRight().setFlipped(true);
                }
                if (board[y][x].getLeft() != null) {
                    board[y][x].getLeft().setFlipped(true);
                }
            }

        }
    }

    /**
     * *********************************************************************
     * Method: printBoard Description: prints the connect four board Parameters:
     * N/A Pre-conditions: Called when the board is needed to be printed in the
     * game Post-conditions: Board is printed
     * *********************************************************************
     */
    public void printBoard() {
        System.out.println("   1 2 3 4 5 6 7 8 9");
        int counter = 1;
        for (Node[] row : board) { //Iterating through all the nodes
            System.out.print(counter + " |");
            counter++;
            for (Node n : row) {
                if (n.isFlagged()) {
                    System.out.print("⚑ ");
                } else if (n.isFlipped()) {
                    if (n.isMined()) {
                        System.out.print("☀ ");
                    } else if (n.getValue() == 0) { //if blank
                        System.out.print("□ ");
                    } else {
                        System.out.print(n.getValue() + " ");
                    }
                } else {
                    System.out.print("■ ");
                }
            }
            System.out.println("");
        }
        System.out.println("Flags left: " + flags);
    }
    //⚑ ☑
    //█ ■ □ ⚀⚁⚂⚃⚄⚅

    public void revealMines() {
        for (Node[] row : board) { //Iterating through all the nodes
            for (Node n : row) {
                if (n.isMined()) {
                    n.setFlipped(true);
                }
            }
        }
    }
    
    public boolean checkWin() {
        int counter = 0;
        for (Node[] row : board) { //Iterating through all the nodes
            for (Node n : row) {
                if (n.isMined() && n.isFlagged()) {
                    counter++;
                }
            }
        }
        if (counter == 10) {
            return true;
        }
        return false;
    }
}
