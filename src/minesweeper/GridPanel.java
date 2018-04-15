package minesweeper;

import javax.swing.*;
import java.awt.*;


// INTEGRATING EVENTS INTO THE GAMEBOARD CLASS

public class GridPanel extends JPanel {
    public GridPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createRaisedBevelBorder());
        setPreferredSize(new Dimension(25, 25));

        // Adjust components
        button.setFocusable(false);

        // Add components
        add(button, BorderLayout.CENTER);

    }

    public Button getButton() {
        return button;
    }

    public JLabel getNearbyBombs() {
        return nearbyBombs;
    }

    public void setNearbyBombs(String num) {
        nearbyBombs.setText(num);
    }

    public Point getPosInGrid() {
        return posInGrid;
    }

    public void setPosInGrid(Point p) {
        posInGrid = p;
    }

    public void addNearbyBombs() {
        if(!nearbyBombs.getText().equals("0")) {
            nearbyBombs.setVisible((true));

            if(nearbyBombs.getText().equals("1")) {
                nearbyBombs.setForeground(Color.BLUE);
            }
            else if(nearbyBombs.getText().equals("2")) {
                nearbyBombs.setForeground(Color.green);
            }
            else if(nearbyBombs.getText().equals("3")) {
                nearbyBombs.setForeground(Color.RED);
            }

            add(nearbyBombs);
            revalidate();
            repaint();
        }
    }

    public void gridBtnLeftClicked() {
        if(!button.getBackground().equals(Color.RED)){
            // Remove the Button
            remove(button);
            setEnabled(false);
            setBorder(BorderFactory.createLoweredBevelBorder());

            // Add the Label
            addNearbyBombs();
        }
    }

    public int gridBtnRightClicked() {
        int result = 0;
        // Mark the panel as a bomb
        if(!button.getBackground().equals(Color.RED)) {
            button.setBackground(Color.RED);
            if (nearbyBombs.getText().equals("-1")) {
                result = 1;
            }
        }
        else {
            if (nearbyBombs.getText().equals("-1")) {
                result = -1;
            }
            button.setBackground(UIManager.getColor("Button.background"));
        }

        return result;
    }

    private Button button = new Button();
    private JLabel nearbyBombs = new JLabel("0", JLabel.CENTER);
    private Point posInGrid;
}
