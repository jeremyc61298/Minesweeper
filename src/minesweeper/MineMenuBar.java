package minesweeper;

import java.awt.*;

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

    public Menu getGame() {
        return game;
    }

    public void setGame(Menu game) {
        this.game = game;
    }

    public MenuItem getHard() {
        return hard;
    }

    public void setHard(MenuItem hard) {
        this.hard = hard;
    }

    public MenuItem getMedium() {
        return medium;
    }

    public void setMedium(MenuItem medium) {
        this.medium = medium;
    }

    public MenuItem getShowBombs() {
        return showBombs;
    }

    public void setShowBombs(MenuItem showBombs) {
        this.showBombs = showBombs;
    }

    public Menu getStartOver() {
        return startOver;
    }

    public void setStartOver(Menu startOver) {
        this.startOver = startOver;
    }

    public MenuItem getViewHighScores() {
        return viewHighScores;
    }

    public void setViewHighScores(MenuItem viewHighScores) {
        this.viewHighScores = viewHighScores;
    }

    public MenuItem getEasy() {
        return easy;
    }

    public void setEasy(MenuItem easy) {
        this.easy = easy;
    }

    private Menu game = new Menu("Game");
    private Menu startOver = new Menu("Start Over");
    private MenuItem easy = new MenuItem("Easy");
    private MenuItem medium = new MenuItem("Medium");
    private MenuItem hard = new MenuItem("Hard");
    private MenuItem showBombs = new MenuItem("Show Bombs");
    private MenuItem viewHighScores = new MenuItem("View High Scores");
}
