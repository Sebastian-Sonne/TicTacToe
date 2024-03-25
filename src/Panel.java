import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Panel class of Tic Tac Toe game, responsibel Panel setup and game master
 * https://github.com/sebastian-sonne/TicTacToe
 *
 * @author Sebastian Sonne
 * @version v1 16.03.2024
 */
public class Panel extends JPanel {
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 500;
    
    private static int moveNum;
    private boolean singlePlayer;

    /**
     * cunstructor of Panel class. initializes and starts game
     */
    Panel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(null);

        moveNum = 0;

        setupTitleScreen();
    }

    /**
     * function to start the game with one player aka against the computer
     */
    private void playComputer() {
        changeToGame();
        singlePlayer = true;
    }

    private void computerMove() {
        String[][] gameState = Computer.copyToStringArray(Setup.getGameButtons());
        int[] computerMove = Computer.miniMax(gameState);
        int row = computerMove[1], col = computerMove[2];
        setPlayerAction(row, col);
    }

    /**
     * function to start game with two players
     */
    private void playFriend() {
        changeToGame();
        singlePlayer = false;
    }

    /**
     * function to change from the title screen to game screen
     */
    private void changeToGame() {
        hideTitleScreen();
        Setup.getResetButton().setVisible(true);

        Setup.gameButtons(this, e -> actionPerformed(e.getActionCommand()));
        Setup.gameBoard(this);
    }

    /**
     * function to set the player action on button in row and col
     * 
     * @param row of button
     * @param col of button
     */
    private void setPlayerAction(int row, int col) {
        JButton[][] buttons = Setup.getGameButtons();

        // set button text: "O", or "X"
        if (buttons[row][col].getText().equals("")) {
            buttons[row][col].setText(Computer.getPlayerTurn());
            buttons[row][col].setEnabled(false);
            setMoveNum(getMoveNum() + 1);
        }

        // check game for winner or tie
        String[][] gameState = Computer.copyToStringArray(buttons);
        int gameEvaluation = Computer.isTerminal(gameState, moveNum);
        if (gameEvaluation != 10) {
            disableGameButtons();
            showWinner(gameEvaluation);
        }
    }

    /**
     * function to show the game eval dialog
     * 
     * @param gameState final gameState evaluation: -1, 0, 1 possible
     * @param panel     reference to JPanel to show dialog
     */
    public void showWinner(int gameState) {
        String[] options = { "Quit", "Restart" };
        String message;

        // convert input String into relevant message
        switch (gameState) {
            case -1:
                message = "Player O won!";
                break;
            case 1:
                message = "Player X won!";
                break;
            default:
                message = "It's a Tie!";
                break;
        }

        int choice = JOptionPane.showOptionDialog(this, message, "Game Results", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

        switch (choice) {
            case JOptionPane.YES_OPTION: // Quit
                System.exit(0);
                break;
            case JOptionPane.NO_OPTION: // Restart
                resetGame();
                break;
        }
    }

    /**
     * function to reset the game
     */
    public void resetGame() {
        setMoveNum(0);
        enableGameButtons();

        JButton[][] buttons = Setup.getGameButtons();

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    /**
     * function to hide the title screen elements
     */
    private void hideTitleScreen() {
        Setup.getSinglePlayerButton().setVisible(false);
        Setup.getMultiPlayerButton().setVisible(false);
        Setup.getDescriptionLabel().setVisible(false);
        repaint();
    }

    /**
     * function to check for action events on buttons
     */
    public void actionPerformed(String actionCommand) {
        int row, col;

        switch (actionCommand) {
            case "0:0":
            case "0:1":
            case "0:2":
            case "1:0":
            case "1:1":
            case "1:2":
            case "2:0":
            case "2:1":
            case "2:2":
                row = Integer.parseInt(actionCommand.substring(0, 1));
                col = Integer.parseInt(actionCommand.substring(2, 3));
                setPlayerAction(row, col);
                break;
        }
    }

    /**
     * KeyListener Listining for Keys clicked
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;

                // Game Control Via KeyBoard
                case KeyEvent.VK_1:
                    setPlayerAction(0, 0);
                    break;
                case KeyEvent.VK_2:
                    setPlayerAction(1, 0);
                    break;
                case KeyEvent.VK_3:
                    setPlayerAction(2, 0);
                    break;
                case KeyEvent.VK_4:
                    setPlayerAction(0, 1);
                    break;
                case KeyEvent.VK_5:
                    setPlayerAction(1, 1);
                    break;
                case KeyEvent.VK_6:
                    setPlayerAction(2, 1);
                    break;
                case KeyEvent.VK_7:
                    setPlayerAction(0, 2);
                    break;
                case KeyEvent.VK_8:
                    setPlayerAction(1, 2);
                    break;
                case KeyEvent.VK_9:
                    setPlayerAction(2, 2);
                    break;
            }
        }
    }

    /*
     * game functions
     */

    /**
     * function to disable game buttons
     */
    private void disableGameButtons() {
        for (JButton[] buttonRow : Setup.getGameButtons()) {
            for (JButton button : buttonRow) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * function to enable game buttons
     */
    private void enableGameButtons() {
        for (JButton[] buttonRow : Setup.getGameButtons()) {
            for (JButton button : buttonRow) {
                button.setEnabled(true);
            }
        }
    }

    /*
     * getter setter methods
     */

    /**
     * @return moveNum
     */
    public static int getMoveNum() {
        return moveNum;
    }

    /**
     * @param val new value of moveNum
     */
    public static void setMoveNum(int val) {
        moveNum = val;
    }

    /*
     * SETUP FUNCTIONS - uses Setup class
     */


    /**
     * function to draw the title screen
     */
    private void setupTitleScreen() {
        Setup.titleLabel(this);
        Setup.descriptionLabel(this);
        Setup.copyrightLabel(this);
        Setup.functionButtons(this, e -> resetGame(), e -> playComputer(),
                e -> playFriend());
    }
}
