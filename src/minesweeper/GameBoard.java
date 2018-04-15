package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {

    public GameBoard(int difficulty) {
        switch (difficulty) {
            case 2:
                ROWS = 20;
                COLS = 20;
                BOMBS = 50;
                break;
            case 3:
                ROWS = 20;
                COLS = 30;
                BOMBS = 100;
                break;
            default:
                ROWS = 10;
                COLS = 10;
                BOMBS = 10;
        }
        setLayout(new GridLayout(ROWS, COLS));
        initGameBoard();
    }

    private void initGameBoard() {

        initGrid();
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

        createClickListeners();
        initNumGrid();

        // Set nearbyBombs in each gridPanel
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
            bombLocations.add(new Point(randRow, randCols));
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

    private void createClickListeners() {
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
                            }
                            else if (gridPanel.getNearbyBombs().getText().equals("-1")) {
                                endGame(false);
                            }
                        }
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            bombsFound += gridPanel.gridBtnRightClicked();
                            if (bombsFound == BOMBS) {
                                endGame(true);
                            }
                        }
                    }
                });
            });
        });
    }

    private void endGame(boolean didWin) {
        // The user has finished the game.
        // Disable the gameBoard
        disableGameBoard(this);

        if (!didWin) {
            revealBombs();
        }
    }

    private void disableGameBoard(Container container) {
        Component[] components = container.getComponents();
            for (int i = 0; i < components.length; i++) {
                components[i].setEnabled(false);
                if (components[i] instanceof  Container) {
                    disableGameBoard((Container)components[i]);
                }
        }
    }

    private void revealBombs() {
        bombLocations.forEach(point -> {
           grid.get(point.x).get(point.y).gridBtnLeftClicked();
        });
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
    private int bombsFound = 0;
    private List<Point> bombLocations = new ArrayList<>();
    private List<List<GridPanel>> grid = new ArrayList<>();
    private List<List<Integer>> numGrid = new ArrayList<>();
}
