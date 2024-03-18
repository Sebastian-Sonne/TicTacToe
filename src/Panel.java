import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Panel class of Tic Tac Toe game, resporibel for all the game functions
 * https://github.com/sebastian-sonne/TicTacToe
 *
 * @author Sebastian Sonne
 * @version v1 16.03.2024
 */
public class Panel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;
    private final int UNIT = 100;
    private final int GAP = 2;
    private final int COL_START = SCREEN_WIDTH / 5;
    private final int ROW_START = SCREEN_HEIGHT / 5;

    private JButton[][] buttons;
    private JButton resetButton;

    private int moveNum = 0;

    /**
     * cunstructor of Panel class. initializes and starts game
     */
    Panel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(null);

        functionButtonSetup();
        gameButtonSetup();
    }

    /**
     * function to check for win/draw
     * 
     * @return null, "tie", "O", "X"
     */
    private String checkGame() {
        // Check horizontal and vertical lines
        for (int i = 0; i < buttons.length; i++) {
            if (checkLine(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText()))
                return buttons[i][0].getText();

            if (checkLine(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText()))
                return buttons[0][i].getText();
        }

        // Check diagonals
        if (checkLine(buttons[0][0].getText(), buttons[1][1].getText(), buttons[2][2].getText())
                || checkLine(buttons[0][2].getText(), buttons[1][1].getText(), buttons[2][0].getText()))
            return buttons[1][1].getText();

        if (moveNum == 9)
        return "tie";

        return null;
    }

    /**
     * function to check if three Strings are equals and not empty
     * 
     * @param a first String
     * @param b second String
     * @param c third String
     * @return true if equal and not empty
     */
    private boolean checkLine(String a, String b, String c) {
        return !a.isEmpty() && a.equals(b) && a.equals(c);
    }

    /**
     * function to set up all function buttons (reset button)
     */
    private void functionButtonSetup() {
        resetButton = new JButton("Reset");
        resetButton.setBounds(10, SCREEN_HEIGHT - 40, 50, 20);
        resetButton.setBorder(null);
        resetButton.setFont(new Font("SANS_SERIF", Font.PLAIN, 16));
        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.gray);
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> resetGame());

        
        this.add(resetButton);
    }

    /**
     * function to setup all 9 JBUttons
     */
    private void gameButtonSetup() {
        buttons = new JButton[3][3];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBorder(null);
                buttons[i][j].setFont(new Font("SANS_SERIF", Font.PLAIN, 96));
                buttons[i][j].setBackground(Color.black);
                buttons[i][j].setForeground(Color.white);
                buttons[i][j].setFocusable(false);

                int x = COL_START + UNIT * i;
                int y = ROW_START + UNIT * j;
                buttons[i][j].setBounds(x + GAP, y + GAP, UNIT - GAP, UNIT - GAP);

                buttons[i][j].addActionListener(this);
                buttons[i][j].setActionCommand(i + ":" + j);

                this.add(buttons[i][j]);
            }
        }
    }

    /**
     * function to return the symbol of the players turn
     * 
     * @return X or O
     */
    private String getPlayer() {
        return (moveNum++ % 2 == 0) ? "X" : "O";
    }

    /**
     * function to set the player action on button in row and col
     * @param row of button
     * @param col of button
     */
    private void setPlayerAction(int row, int col) {
        //set button text: "O", or "X"
        if (buttons[row][col].getText().equals(""))
            buttons[row][col].setText(getPlayer());

        //check game for winner or tie
        String game = checkGame();
        if (game != null)
            showWinnerScreen(game);
    }

    /**
     * function to show the winner screen
     * 
     * @param message accepts "tie", "O", "X"
     */
    private void showWinnerScreen(String message) {
        String[] options = { "Quit", "Restart" };

        // convert input String into relevant message
        switch (message) {
            case "tie":
                message = "It's a Tie!";
                break;
            case "O":
                message = "Player O won!";
                break;
            case "X":
                message = "Player X won!";
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
        moveNum = 0;

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

    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        drawString("Tic Tac Toe", 130, 50, g);
        drawGrid(g);
    }

    /**
     * function to draw the Tic Tac Toe grid
     * 
     * @param g Graphics g
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.gray);

        for (int i = 1; i < 3; i++) {
            // vertical lines
            g.fillRect(COL_START + UNIT * i, ROW_START, 2, 3 * UNIT);
            // horizontal lines
            g.fillRect(COL_START, ROW_START + UNIT * i, 3 * UNIT, 2);
        }
    }

    /**
     * function to draw a String s at a specific position
     * @param s String to be drawn
     * @param x x position of String
     * @param y y position of String
     * @param g Graphics g
     */
    private void drawString(String s, int x, int y, Graphics g) {
        g.setFont(new Font("SANS_SERIF", Font.PLAIN, 48));
        g.drawString(s, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
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
            }
        }
    }
}
