package minesweeper;

import java.util.List;
import java.awt.Point;
import java.util.Stack;

public class TestCoordinates {

    public TestCoordinates(int maxrows, int maxcols, List<List<Integer>> numGrid) {

        this.MAXROWS = maxrows;
        this.MAXCOLS = maxcols;
        this.numGrid = numGrid;
    }

    public TestCoordinates(int maxrows, int maxcols, List<List<Integer>> numGrid, List<List<GridPanel>> grid) {

        this.MAXROWS = maxrows;
        this.MAXCOLS = maxcols;
        this.numGrid = numGrid;
        this.grid = grid;
    }

    public void testAll(int x, int y) {
        this.row = x;
        this.column = y;

        testNorth();
        testEast();
        testSouth();
        testWest();
        testNorthEast();
        testSouthEast();
        testSouthWest();
        testNorthWest();
    }

    public void testForZeros(GridPanel gridPanel) {
        // Initialize class stack and gridPanel
        gp = gridPanel;
        possibleMoves = new Stack<>();
        Point p = gp.getPosInGrid();
        possibleMoves.push(p);

        do {
            numGrid.get(p.x).set(p.y, BREADCRUMB);
            testNorth(new Point(p.x - 1, p.y));
            testNorthEast(new Point(p.x - 1, p.y + 1));
            testEast(new Point(p.x, p.y + 1));
            testSouthEast(new Point(p.x + 1, p.y + 1));
            testSouth(new Point(p.x + 1, p.y));
            testSouthWest(new Point(p.x + 1, p.y - 1));
            testWest(new Point(p.x, p.y - 1));
            testNorthWest(new Point(p.x - 1, p.y - 1));

            // This if statement will hopefully stop some overhead when
            // a button has already been disabled
            if ( grid.get(p.x).get(p.y).getButton().isEnabled()) {
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
            p = possibleMoves.pop();
        } while(!possibleMoves.empty());
    }

    private void testNorth() {
        if (row - 1 < 0 || numGrid.get(row - 1).get(column) == -1) {
            return;
        }
        int temp = numGrid.get(row - 1).get(column);
        numGrid.get(row - 1).set(column, ++temp);
    }

    private void testNorth(Point p) {
        if (p.x >= 0) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testEast() {
        if (column + 1 >= MAXCOLS || numGrid.get(row).get(column + 1) == -1) {
            return;
        }
        int temp = numGrid.get(row).get(column + 1);
        numGrid.get(row).set(column + 1, ++temp);
    }

    private void testEast(Point p) {
        if (p.y < MAXCOLS) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testSouth() {
        if (row + 1 >= MAXROWS || numGrid.get(row + 1).get(column) == -1) {
            return;
        }
        int temp = numGrid.get(row + 1).get(column);
        numGrid.get(row + 1).set(column, ++temp);
    }

    private void testSouth(Point p) {
        if (p.x < MAXROWS) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testWest() {
        if (column - 1 < 0 || numGrid.get(row).get(column - 1) == -1) {
            return;
        }
        int temp = numGrid.get(row).get(column - 1);
        numGrid.get(row).set(column - 1, ++temp);
    }

    private void testWest(Point p) {
        if (p.y >= 0) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testNorthEast() {
        if (row - 1 < 0 || column + 1 >= MAXCOLS
                || numGrid.get(row - 1).get(column + 1) == -1) {

            return;
        }
        int temp = numGrid.get(row - 1).get(column + 1);
        numGrid.get(row - 1).set(column + 1, ++temp);
    }

    private void testNorthEast(Point p) {
        if (p.x >= 0 && p.y < MAXCOLS) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testSouthEast() {
        if (row + 1 >= MAXROWS || column + 1 >= MAXCOLS
                || numGrid.get(row + 1).get(column + 1) == -1) {

            return;
        }
        int temp = numGrid.get(row + 1).get(column + 1);
        numGrid.get(row + 1).set(column + 1, ++temp);
    }

    private void testSouthEast(Point p) {
        if (p.x < MAXROWS && p.y < MAXCOLS) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testSouthWest() {
        if (row + 1 >= MAXROWS || column - 1 < 0
                || numGrid.get(row + 1).get(column - 1) == -1) {

            return;
        }
        int temp = numGrid.get(row + 1).get(column - 1);
        numGrid.get(row + 1).set(column - 1, ++temp);
    }

    private void testSouthWest(Point p) {
        if (p.x < MAXROWS && p.y >= 0) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private void testNorthWest() {
        if (row - 1 < 0 || column - 1 < 0
                || numGrid.get(row - 1).get(column - 1) == -1) {

            return;
        }
        int temp = numGrid.get(row - 1).get(column - 1);
        numGrid.get(row - 1).set(column - 1, ++temp);
    }

    private void testNorthWest(Point p) {
        if (p.x >= 0 && p.y >= 0) {
            if (numGrid.get(p.x).get(p.y) == 0) {
                possibleMoves.push(p);
            }
            else if (grid.get(p.x).get(p.y).getButton().isEnabled()){
                grid.get(p.x).get(p.y).gridBtnLeftClicked();
            }
        }
    }

    private int row;
    private int column;
    private int MAXROWS;
    private int MAXCOLS;
    private final Integer BREADCRUMB = -2;
    private GridPanel gp = null;
    private Stack<Point> possibleMoves = null;
    private List<List<Integer>> numGrid;
    private List<List<GridPanel>> grid = null;
}
