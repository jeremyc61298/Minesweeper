package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {

    public GameBoard(int rows, int cols) {
        this.ROWS = rows;
        this.COLS = cols;
        this.BOMBS = 10;
        setLayout(new GridLayout(ROWS, COLS));
        initGameBoard();
    }

    private void initGameBoard() {

        initGrid();
        initNumGrid();

        this.setPreferredSize(new Dimension(300, 300));
        // Print the numGrid
        numGrid.forEach((rowList) -> {
            rowList.forEach((colItem) -> {
                System.out.print(colItem);
            });
            System.out.println();
        });
    }


    private void initGrid() {
        // Fill the grid with GridButtons
        for (int i = 0; i < ROWS; i++) {
            grid.add(new ArrayList<GridPanel>());

            for (int j = 0; j < COLS; j++) {
                GridPanel newGridPanel = new GridPanel();
                grid.get(i).add(newGridPanel);
                add(newGridPanel);
            }
        }

        // Create mouse listeners for each button
        grid.forEach((colList) -> {
            colList.forEach((gridPanel) -> {
                gridPanel.getButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gridBtnClicked(e, gridPanel);
                    }
                });
            });
        });
    }

    private void initNumGrid() {
        // Fill the numGrid with 0's
        for (int i = 0; i < ROWS; i++) {
            numGrid.add(new ArrayList<Integer>());

            for (int j = 0; j < COLS; j++) {
                numGrid.get(i).add(0);
            }
        }

        // Place bombs
        Random rand = new Random();
        for (int i = 0; i < BOMBS; i++) {
            int randRow = rand.nextInt(ROWS);
            int randCols = rand.nextInt(COLS);
            numGrid.get(randRow).set(randCols, -1);
        }

        // Fill in the gaps
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (numGrid.get(i).get(j).equals(-1)) {
                    TestCoordinates tc = new TestCoordinates(i, j, ROWS, COLS, numGrid);
                }
            }
        }
    }

    private void gridBtnClicked(MouseEvent e, GridPanel gridPanel) {
        // Remove the button
        gridPanel.remove(gridPanel.getButton());
        gridPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        // Add the Label NOT WORKING
        gridPanel.add(gridPanel.getNearbyBombs());

    }

    private final int ROWS;
    private final int COLS;
    private final int BOMBS;
    private List<List<GridPanel>> grid = new ArrayList<List<GridPanel>>();
    private List<List<Integer>> numGrid = new ArrayList<List<Integer>>();
}
