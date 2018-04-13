package minesweeper;

import javax.swing.JFrame;
import java.awt.FlowLayout;


public class Minesweeper extends JFrame {

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(500, 500);
        // Add components...
        setMenuBar(menuBar);
        add(gameboard);

        // Add padding around the Gameboard

        setVisible(true);
    }

    private MineMenuBar menuBar = new MineMenuBar();
    private GameBoard gameboard = new GameBoard(10, 10);

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
    }
}
