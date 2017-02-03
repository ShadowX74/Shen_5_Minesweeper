/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ShadowX
 */
public class Game {

    //scanner for input
    static Scanner scan;
    //boolean to loop game
    static boolean play = true;
    static Minefield map = new Minefield();

    public static void run() {
        map.printBoard();
        while (play) {
            scan = new Scanner(System.in);
            System.out.println("Would you like to flag the next square? (Y/N)");
            String choice = scan.nextLine().toUpperCase();
            boolean flag = false;
            if (choice.equals("Y")) {
                flag = true;
            }
            System.out.println("What row would you like to select?");
            int row = scan.nextInt() - 1;
            System.out.println("What col would you like to select?");
            int col = scan.nextInt() - 1;

            if (row > 8 || col > 8 || row < 0 || col < 0) {
                System.out.println("Try again");
            } else if (map.board[row][col].isFlagged()) {
                System.out.println("This place has been flagged. Would you like to unflag it? (Y/N)");
                scan = new Scanner(System.in);
                choice = scan.nextLine().toUpperCase();
                if (choice.equals("Y")) {
                    map.board[row][col].setFlagged(false);
                    map.flags++;
                }
            } else if (flag) {
                if (map.flags > 0) {
                    map.board[row][col].setFlagged(flag);
                    map.flags--;
                } else {
                    System.out.println("Not enough mines.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (map.board[row][col].isMined()) {
                map.revealMines();
                play = false;
                System.out.println("YOU HIT A MINE!!!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                map.detectZeroes(row, col);
                map.board[row][col].setFlipped(true);
            }
            if (map.checkWin()) {
                System.out.println("You won!");
                map.flipAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                play = false;
            }
            System.out.println("");
            map.printBoard();
        }
    }
}
