package minesweeper;

import java.awt.MenuBar;
import java.awt.Menu;

public class MineMenuBar extends MenuBar {

    public MineMenuBar() {
        initMenus();
    }

    private void initMenus() {
        add(game);
    }

    private Menu game = new Menu("Game");
}
