package minesweeper;

import javax.swing.*;


public class Minesweeper extends JFrame {

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        // Add components...
        add(gameboard);

        // Add padding around the Gameboard

        setVisible(true);
    }

    private GameBoard gameboard = new GameBoard(10, 10);

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
    }
}
