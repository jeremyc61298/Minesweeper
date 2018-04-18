package minesweeper;

import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Menu;

public class MineMenuBar extends MenuBar {

    public MineMenuBar() {
        initMenus();
        initMenuBar();
    }

    private void initMenus() {
        startOver.add(easy);
        startOver.add(medium);
        startOver.add(hard);

        game.add(startOver);
        game.add(showBombs);
        game.add(viewHighScores);
    }

    private void initMenuBar() {
        add(game);
    }

    public MenuItem getHard() {
        return hard;
    }

    public MenuItem getMedium() {
        return medium;
    }

    public MenuItem getShowBombs() {
        return showBombs;
    }

    public MenuItem getViewHighScores() {
        return viewHighScores;
    }

    public MenuItem getEasy() {
        return easy;
    }

    private Menu game = new Menu("Game");
    private Menu startOver = new Menu("Start Over");
    private MenuItem easy = new MenuItem("Easy");
    private MenuItem medium = new MenuItem("Medium");
    private MenuItem hard = new MenuItem("Hard");
    private MenuItem showBombs = new MenuItem("Show Bombs");
    private MenuItem viewHighScores = new MenuItem("View High Scores");
}
