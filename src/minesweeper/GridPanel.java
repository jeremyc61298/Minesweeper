package minesweeper;

import javax.swing.JPanel;
import java.awt.*;


// INTEGRATING EVENTS INTO THE GAMEBOARD CLASS

public class GridPanel extends JPanel {
    public GridPanel() {
        setLayout(new GridLayout(1, 1));
        add(button);
    }

    public Button getButton() {
        return button;
    }

    // MIGHT NOT USE THIS FUNCTIONALITY
    public int getNearbyBombs() {
        return nearbyBombs;
    }

    public void setNearbyBombs(int bombs) {
        nearbyBombs = bombs;
    }

    public void incrementNearbyBombs() {
        nearbyBombs++;
    }

    // MIGHT NOT USE

    private Button button = new Button();
    private int nearbyBombs;
}
