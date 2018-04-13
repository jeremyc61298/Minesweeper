package minesweeper;

import javax.swing.*;
import java.awt.Button;
import java.awt.GridLayout;


// INTEGRATING EVENTS INTO THE GAMEBOARD CLASS

public class GridPanel extends JPanel {
    public GridPanel() {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createRaisedBevelBorder());

        // Adjust components
        button.setFocusable(false);

        // Add components
        add(button);
    }

    public Button getButton() {
        return button;
    }

    public JLabel getNearbyBombs() {
        return nearbyBombs;
    }

    private Button button = new Button();
    private JLabel nearbyBombs = new JLabel("1");
}
