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

        this.setPreferredSize(new Dimension(300, 300));

        printNumGrid();
    }


    private void initGrid() {
        // Fill the grid with GridButtons
        for (int i = 0; i < ROWS; i++) {
            grid.add(new ArrayList<GridPanel>());

            for (int j = 0; j < COLS; j++) {
                GridPanel newGridPanel = new GridPanel();
                newGridPanel.setPosInGrid(new Point(i, j));
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
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            gridPanel.gridBtnLeftClicked();

                            if (gridPanel.getNearbyBombs().getText().equals("0")) {
                                TestCoordinates tc = new TestCoordinates(ROWS, COLS, numGrid, grid);
                                tc.testForZeros(gridPanel);
                                printNumGrid();
                            }
                        }
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            gridPanel.gridBtnRightClicked();
                        }
                    }
                });
            });
        });

        initNumGrid();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                String num = numGrid.get(i).get(j).toString();
                grid.get(i).get(j).setNearbyBombs(num);
            }
        }
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
        int randRow = rand.nextInt(ROWS - 1);
        int randCols = rand.nextInt(COLS - 1);

        for (int i = 0; i < BOMBS; i++) {
            while (numGrid.get(randRow).get(randCols) == -1) {
                randRow = rand.nextInt(ROWS - 1);
                randCols = rand.nextInt(COLS - 1);
            }
            numGrid.get(randRow).set(randCols, -1);
        }

        // Fill in the gaps
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (numGrid.get(i).get(j).equals(-1)) {
                    TestCoordinates tc = new TestCoordinates(ROWS, COLS, numGrid);
                    tc.testAll(i, j);
                }
            }
        }
    }


    private void printNumGrid() {
        // Print the numGrid
        numGrid.forEach((rowList) -> {
            rowList.forEach((colItem) -> {
                System.out.print(colItem);
            });
            System.out.println();
        });
    }

    private final int ROWS;
    private final int COLS;
    private final int BOMBS;
    private List<List<GridPanel>> grid = new ArrayList<>();
    private List<List<Integer>> numGrid = new ArrayList<>();
}
