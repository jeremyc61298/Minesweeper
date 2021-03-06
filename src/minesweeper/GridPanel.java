package minesweeper;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Image;

// Originally, this class HAS-A button. That is no longer so because of the overhead
// that was occurring each time a gameboard was created.
public class GridPanel extends JPanel {
    public GridPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createRaisedBevelBorder());
        setPreferredSize(new Dimension(25, 25));
        // Bomb icon drawn by my wonderful wife, Courtney
        bomb = new ImageIcon(new ImageIcon("Images/mine.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    }


    public JLabel getNearbyBombs() {
        return nearbyBombs;
    }

    public void setNearbyBombs(String num) {
       if (num.equals("-1")) {
           nearbyBombs = new JLabel(bomb);
           nearbyBombs.setText("");
       }
       else {
           nearbyBombs.setText(num);
       }
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

    public boolean gridBtnLeftClicked() {
        boolean result = true;
        if(!getBackground().equals(Color.RED)){
            setEnabled(false);
            setBorder(BorderFactory.createLoweredBevelBorder());

            // Add the Label
            addNearbyBombs();
            result = false;
        }
        return result;
    }

    public void gridBtnRightClicked(GameBoard.Pair pair, int BOMBS) {

        // Mark the panel as a bomb
        if(!getBackground().equals(Color.RED) && pair.flags != BOMBS) {
            setBackground(Color.RED);
            if (nearbyBombs.getText().equals("")) {
                pair.bombs++;
            }
            pair.flags++;
        }
        else {
            if (nearbyBombs.getText().equals("")) {
                pair.bombs--;
            }

            if (getBackground().equals(Color.RED)) {
                pair.flags--;
            }
            setBackground(UIManager.getColor("Button.background"));
        }
    }


    private JLabel nearbyBombs = new JLabel("0", JLabel.CENTER);
    private Point posInGrid;
    private ImageIcon bomb = null;
}
