package minesweeper;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.beans.PropertyChangeListener;

public class GameBoard extends JPanel {

    public GameBoard(int difficulty) {
        this.DIFFICULTY = difficulty;
            switch (DIFFICULTY) {
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
        startTime = System.nanoTime();
    }


    private void initGrid() {
        // Fill the grid with GridButtons
        for (int i = 0; i < ROWS; i++) {
            grid.add(new ArrayList<>());

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
            numGrid.add(new ArrayList<>());

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
        // Create mouse listeners for each gridPanel
        grid.forEach((colList) -> {
            colList.forEach((gridPanel) -> {
                gridPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (gridPanel.isEnabled()) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                boolean isRed = gridPanel.gridBtnLeftClicked();

                                // If the user clicked a panel touching no bombs
                                if (!isRed && gridPanel.getNearbyBombs().getText().equals("0")) {
                                    TestCoordinates tc = new TestCoordinates(ROWS, COLS, numGrid, grid);
                                    tc.testForZeros(gridPanel);
                                }
                                else if (!isRed && gridPanel.getNearbyBombs().getText().equals("")) {
                                    // The user has clicked a bomb
                                    endGame();
                                }
                            }
                            else if (e.getButton() == MouseEvent.BUTTON3){
                                Pair pair = new Pair(bombsFound, flagsLaid);
                                gridPanel.gridBtnRightClicked(pair, BOMBS);
                                bombsFound = pair.bombs;
                                flagsLaid = pair.flags;
                                if (bombsFound == BOMBS) {
                                    recordTime();
                                    didWin = true;
                                    endGame();
                                }
                            }
                        }
                    }
                });
            });
        });
    }

    // This function allows the use of a bound property I defined to be listened to
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        addPropertyChangeListener(listener);
    }

    private void endGame() {
        // The user has finished the game.
        // Alert the user and disable the gameboard
        if (!didWin) {
            revealBombs();
            JOptionPane.showMessageDialog(null, "You lost. Nice try!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/mine.png"));
        }
        else {
            JOptionPane.showMessageDialog(null, "You won!\nScore: " + totalTime + " seconds",
                    "Winner!", JOptionPane.INFORMATION_MESSAGE);
        }

        disableGameBoard(this);
        setEnabled(false);
    }

    // This recursive function will disable all the child components of the gameboard
    // Link: https://coderanch.com/t/332174/java/Disabling-JPanel-components
    public void disableGameBoard(Container container) {
        Component[] components = container.getComponents();
            for (int i = 0; i < components.length; i++) {
                components[i].setEnabled(false);
                if (components[i] instanceof  Container) {
                    disableGameBoard((Container)components[i]);
                }
        }
    }

    private void recordTime() {
        setTotalTime((System.nanoTime() - startTime)/1000/1000/1000);
    }

    public void setDidWin(boolean didWin) {
        this.didWin = didWin;
    }

    public void revealBombs() {
        bombLocations.forEach(point -> {
           grid.get(point.x).get(point.y).gridBtnLeftClicked();
        });
    }

    // This function fires a propertyChange event, making it a bound property
    public void setTotalTime(long time) {
        long oldTime = totalTime;
        totalTime = time;
        firePropertyChange("totalTime", oldTime, totalTime);
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int getDifficulty() {
        return DIFFICULTY;
    }

    // This class is used to correlate the bombs and flags variables,
    // so they can both be modified and changed in my gridBtnRightClicked function
    public class Pair {
        public Pair(int bombs, int flags) {
            this.bombs = bombs;
            this.flags = flags;
        }
        public int bombs;
        public int flags;
    }

    private final int ROWS;
    private final int COLS;
    private final int BOMBS;
    private final int DIFFICULTY;
    private int bombsFound = 0;
    private int flagsLaid = 0;
    private long startTime;
    private long totalTime = 0;
    private boolean didWin = false;
    private List<Point> bombLocations = new ArrayList<>();
    private List<List<GridPanel>> grid = new ArrayList<>();
    private List<List<Integer>> numGrid = new ArrayList<>();

}
