package minesweeper;

import java.util.List;

// BOUND CASES STILL ARE THROWING OUT OF RANGE EXCEPTIONS

public class TestCoordinates {

    public TestCoordinates(int x, int y, int maxrows, int maxcols, List<List<Integer>> numGrid) {
        this.row = x;
        this.column = y;
        this.MAXROWS = maxrows;
        this.MAXCOLS = maxcols;
        this.numGrid = numGrid;
        testNorth();
        testEast();
        testSouth();
        testWest();
        testNorthEast();
        testSouthEast();
        testSouthWest();
        testNorthWest();
    }

    public void testNorth() {
        if (row - 1 < 0 || numGrid.get(row - 1).get(column) == -1) {
            return;
        }
        int temp = numGrid.get(row - 1).get(column);
        numGrid.get(row - 1).set(column, ++temp);
    }

    public void testEast() {
        if (column + 1 >= MAXCOLS || numGrid.get(row).get(column + 1) == -1) {
            return;
        }
        int temp = numGrid.get(row).get(column + 1);
        numGrid.get(row).set(column + 1, ++temp);
    }

    public void testSouth() {
        if (row + 1 >= MAXROWS || numGrid.get(row + 1).get(column) == -1) {
            return;
        }
        int temp = numGrid.get(row + 1).get(column);
        numGrid.get(row + 1).set(column, ++temp);
    }

    public void testWest() {
        if (column - 1 < 0 || numGrid.get(row).get(column - 1) == -1) {
            return;
        }
        int temp = numGrid.get(row).get(column - 1);
        numGrid.get(row).set(column - 1, ++temp);
    }

    public void testNorthEast() {
        if (row - 1 < 0 || column + 1 >= MAXCOLS
                || numGrid.get(row - 1).get(column) == -1) {

            return;
        }
        int temp = numGrid.get(row - 1).get(column + 1);
        numGrid.get(row - 1).set(column + 1, ++temp);
    }

    public void testSouthEast() {
        if (row + 1 >= MAXROWS || column + 1 >= MAXCOLS
                || numGrid.get(row + 1).get(column + 1) == -1) {

            return;
        }
        int temp = numGrid.get(row + 1).get(column + 1);
        numGrid.get(row + 1).set(column + 1, ++temp);
    }

    public void testSouthWest() {
        if (row + 1 >= MAXROWS || column - 1 < 0
                || numGrid.get(row + 1).get(column - 1) == -1) {

            return;
        }
        int temp = numGrid.get(row + 1).get(column - 1);
        numGrid.get(row + 1).set(column - 1, ++temp);
    }

    public void testNorthWest() {
        if (row - 1 < 0 || column - 1 < 0
                || numGrid.get(row - 1).get(column - 1) == -1) {

            return;
        }
        int temp = numGrid.get(row).get(column);
        numGrid.get(row - 1).set(column - 1, ++temp);
    }

    private int row;
    private int column;
    private int MAXROWS;
    private int MAXCOLS;
    private List<List<Integer>> numGrid;
}
