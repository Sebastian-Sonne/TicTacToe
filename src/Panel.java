import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Panel class of Tic Tac Toe game, resporibel for all the game functions
 * https://github.com/sebastian-sonne/TicTacToe
 *
 * @author Sebastian Sonne
 * @version v1 16.03.2024
 */
public class Panel extends JPanel {
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 500;
    private final int UNIT = 100;
    public final int GAP = 2;
    private final int COL_START = SCREEN_WIDTH / 5;
    private final int ROW_START = SCREEN_HEIGHT / 5;

    private JButton[][] buttons;
    private JButton resetButton;
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;

    private JLabel copyrightLabel;
    private JLabel titleLabel;
    private JLabel instructionsLabel;

    private static int moveNum;
    private boolean wasEvaluated;
    private boolean initialLoad;
    private boolean drawBoard;
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
        wasEvaluated = false;
        initialLoad = true;
        drawBoard = false;
    }

    /**
     * function to set up all function buttons
     */
    private void setupFunctionButtons() {
        resetButton = new JButton("Reset");
        singlePlayerButton = new JButton("1 Player");
        multiPlayerButton = new JButton("2 Players");

        Setup.functionButtons(resetButton, singlePlayerButton, multiPlayerButton, e -> resetGame(), e -> playComputer(),
                e -> playFriend());

        this.add(resetButton);
        this.add(singlePlayerButton);
        this.add(multiPlayerButton);
    }

    /**
     * function to setup all 9 JBUttons
     */
    private void gameButtonSetup() {
        buttons = new JButton[3][3];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {

                buttons[i][j] = new JButton();
                Setup.gameButtons(buttons[i][j], i, j, e -> actionPerformed(e.getActionCommand()));
                this.add(buttons[i][j]);
            }
        }
    }

    /**
     * function to set up the copyright label
     */
    private void setUpCopyright() {
        copyrightLabel = new JLabel("Copyright \u00A9 2024 Sebastian Sonne");
        Setup.copyright(copyrightLabel);
        this.add(copyrightLabel);
    }

    /**
     * function to start the game with one player aka against the computer
     */
    private void playComputer() {
        changeToGame();
        singlePlayer = true;
    }

    private void computerMove() {
        String[][] gameState = Computer.copyToStringArray(buttons);
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
        resetButton.setVisible(true);
        setUpCopyright();

        drawBoard = true;
        this.repaint();
        gameButtonSetup();
    }

    /**
     * function to set the player action on button in row and col
     * 
     * @param row of button
     * @param col of button
     */
    private void setPlayerAction(int row, int col) {
        // set button text: "O", or "X"
        if (buttons[row][col].getText().equals("")) {
            buttons[row][col].setText(Computer.getPlayerTurn());
            setMoveNum(getMoveNum() + 1);
        }

        // check game for winner or tie
        String[][] gameState = Computer.copyToStringArray(buttons);
        int gameEvaluation = Computer.isTerminal(gameState, moveNum);
        if (gameEvaluation != 10 && wasEvaluated != true) {
            wasEvaluated = true;
            showWinnerScreen(gameEvaluation);
        }
    }

    /**
     * function to show the winner screen
     * 
     * @param message accepts -1, 0, 1
     */
    private void showWinnerScreen(int gameState) {
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
    private void resetGame() {
        setMoveNum(0);
        wasEvaluated = false;

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * function of paintcomponent
     * 
     * @param g Grpahics
     */
    public void draw(Graphics g) {
        if (initialLoad) {
            setupTitleScreen();
            initialLoad = false;
        }

        if (drawBoard) {
            drawGameBoard(g);
        }
    }

    /**
     * function to draw the title screen
     */
    private void setupTitleScreen() {
        setupTitle(130, 30);
        setupDescription(150, 200);
        setUpCopyright();
        setupFunctionButtons();
    }

    /**
     * function to hide the title screen elements
     */
    private void hideTitleScreen() {
        singlePlayerButton.setVisible(false);
        multiPlayerButton.setVisible(false);
        instructionsLabel.setVisible(false);
    }

    /**
     * function to setup the game title
     * 
     * @param x x-coordinate of title
     * @param y y-coordinate of title
     */
    private void setupTitle(int x, int y) {
        titleLabel = new JLabel("Tic Tac Toe");
        Setup.title(titleLabel, x, y);
        this.add(titleLabel);
    }

    /**
     * function to setup the game instructions
     * 
     * @param x x-coordinate of instruction
     * @param y y-coordinate of instruction
     */
    private void setupDescription(int x, int y) {
        instructionsLabel = new JLabel("Chose Game Mode");
        instructionsLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 22));
        instructionsLabel.setBounds(x, y, 200, 20);
        instructionsLabel.setForeground(Color.white);
        instructionsLabel.setBackground(Color.cyan);
        this.add(instructionsLabel);
    }

    /**
     * function to draw the Tic Tac Toe grid
     * 
     * @param g Graphics g
     */
    private void drawGameBoard(Graphics g) {
        g.setColor(Color.gray);

        for (int i = 1; i < 3; i++) {
            // vertical lines
            g.fillRect(COL_START + UNIT * i, ROW_START, 2, 3 * UNIT);
            // horizontal lines
            g.fillRect(COL_START, ROW_START + UNIT * i, 3 * UNIT, 2);
        }
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
}
