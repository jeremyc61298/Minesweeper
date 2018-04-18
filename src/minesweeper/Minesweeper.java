package minesweeper;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
        // Listeners which create a new gameboard and set the difficulty
        menuBar.getEasy().addActionListener((ActionEvent e) -> {
            remove(gameboard);
            gameboard = new GameBoard(1);
            SCORES_PATH = SCORES_EASY;
            createEndGameListener();
            add(gameboard);
            repaint();
            revalidate();
        });

        menuBar.getMedium().addActionListener((ActionEvent e) -> {
            remove(gameboard);
            gameboard = new GameBoard(2);
            SCORES_PATH = SCORES_MEDIUM;
            createEndGameListener();
            add(gameboard);
            repaint();
            revalidate();
        });

        menuBar.getHard().addActionListener((ActionEvent e) -> {
            remove(gameboard);
            gameboard = new GameBoard(3);
            SCORES_PATH = SCORES_HARD;
            createEndGameListener();
            add(gameboard);
            repaint();
            revalidate();
        });
        // End new gameboard listeners

        menuBar.getShowBombs().addActionListener((ActionEvent e) -> {
            gameboard.setDidWin(false);
            gameboard.disableGameBoard(gameboard);
            gameboard.revealBombs();
            repaint();
            revalidate();
        });

        menuBar.getViewHighScores().addActionListener((ActionEvent e) -> {
            try {
                // Read in the 10 top high scores, or if less than 10, as many as there are
                File scoreFile = new File(SCORES_PATH);
                boolean success = scoreFile.createNewFile();

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

                String difficulty = "Easy";
                if (SCORES_PATH.equals(SCORES_MEDIUM)) {
                    difficulty = "Medium";
                }else if (SCORES_PATH.equals(SCORES_HARD)) {
                    difficulty = "Hard";
                }

                if(scores != "") {
                    JOptionPane.showMessageDialog(null, scores, "High Scores - " + difficulty,
                            JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "There are no high scores.",
                             "High Scores - " + difficulty, JOptionPane.PLAIN_MESSAGE);
                }
            }
            catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "You're scores could not be found.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }

            repaint();
            revalidate();
        });
    }

    private void createEndGameListener() {
        // Property "totalTime" in gameboard is created to be a bound property, and fires
        // an event when it is changed
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
                // Creates the file if it does not exist
                boolean success = scores.createNewFile();

                FileReader fileReader = new FileReader(SCORES_PATH);
                BufferedReader reader = new BufferedReader(fileReader);

                // Populates a list of a maximum of 10 scores
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null && i < 10) {
                    scoreList.add(Integer.parseInt(line));
                    i++;
                }
                reader.close();

                String temp = Long.toString(gameboard.getTotalTime());
                Integer totalTime = Integer.parseInt(temp);

                // Add the new score to the list if it is better than one of the 10
                // scores from the file.
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
    private String SCORES_PATH = "Scores/scoresEasy.txt";
    private String SCORES_EASY = "Scores/scoresEasy.txt";
    private String SCORES_MEDIUM = "Scores/scoresMedium.txt";
    private String SCORES_HARD = "Scores/scoresHard.txt";

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
    }
}
