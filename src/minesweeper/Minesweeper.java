package minesweeper;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class Minesweeper extends JFrame {

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        // OPEN FULLSCREEN?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setIconImage(bomb.getImage());

        createMenuListeners();
        createEndGameListener();

        // Add components...
        setMenuBar(menuBar);
        add(gameboard);


        setVisible(true);
    }

    private void createMenuListeners() {
        menuBar.getEasy().addActionListener((ActionEvent e) -> {
            remove(gameboard);
            gameboard = new GameBoard(1);
            createEndGameListener();
            add(gameboard);
            repaint();
            revalidate();
        });

        menuBar.getMedium().addActionListener((ActionEvent e) -> {
            remove(gameboard);
            gameboard = new GameBoard(2);
            createEndGameListener();
            add(gameboard);
            repaint();
            revalidate();
        });

        menuBar.getHard().addActionListener((ActionEvent e) -> {
            remove(gameboard);
            gameboard = new GameBoard(3);
            createEndGameListener();
            add(gameboard);
            repaint();
            revalidate();
        });

        menuBar.getShowBombs().addActionListener((ActionEvent e) -> {
            gameboard.setDidWin(false);
            gameboard.disableGameBoard(gameboard);
            gameboard.revealBombs();
            repaint();
            revalidate();
        });

        menuBar.getViewHighScores().addActionListener((ActionEvent e) -> {
            // SHOW THE USER THE HIGH SCORES IN A DIALOG BOX?
            try {

                FileReader fileReader = new FileReader(SCORES_PATH);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;
                String scores = "";
                int i = 0;
                while ((line = reader.readLine()) != null && i < 10) {
                    scores = scores + line + " seconds\n";
                    i++;
                }
                reader.close();
                 if(scores != "") {
                     JOptionPane.showMessageDialog(null, scores, "High Scores", JOptionPane.PLAIN_MESSAGE);
                 }
                 else {
                     JOptionPane.showMessageDialog(null, "There are no high scores.", "High Scores", JOptionPane.PLAIN_MESSAGE);
                 }


            }
            catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "You're score could not be saved.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }

            repaint();
            revalidate();
        });
    }

    private void createEndGameListener() {
        gameboard.addPropertyChangeListener("totalTime", (PropertyChangeEvent e) -> {
            // Print out to the high scores file
            System.out.println("Great!");
            addNewScore();
        });
    }

    private void addNewScore() {
            List<Integer> scoreList = new ArrayList<>();
            File scores = new File(SCORES_PATH);

            try {
                scores.createNewFile();

                FileReader fileReader = new FileReader(SCORES_PATH);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;
                int i = 0;
                while ((line = reader.readLine()) != null && i < 10) {
                    scoreList.add(Integer.parseInt(line));
                    i++;
                }
                reader.close();

                String temp = Long.toString(gameboard.getTotalTime());
                Integer totalTime = Integer.parseInt(temp);

                if(scoreList.size() == 10 && scoreList.get(scoreList.size() - 1) > totalTime) {
                    scoreList.set(scoreList.size() - 1, totalTime);
                }
                else if(scoreList.size() != 0 && scoreList.get(scoreList.size() - 1) > totalTime) {
                    scoreList.add(totalTime);
                }

                // Sort the scores
                Collections.sort(scoreList);

                // Write the scores back to the file
                FileWriter fileWriter = new FileWriter(SCORES_PATH);
                BufferedWriter writer = new BufferedWriter(fileWriter);

                for(int j = 0; j < scoreList.size(); j++) {
                    writer.write(scoreList.get(j).toString());
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            }
            catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "You're score could not be saved.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
    }

    private MineMenuBar menuBar = new MineMenuBar();
    private GameBoard gameboard = new GameBoard(1);
    private ImageIcon bomb = new ImageIcon("Images/mine.png");
    private String SCORES_PATH = "Scores/scores.txt";

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
    }
}
