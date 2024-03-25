import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class to setup elements of JPanel for TicTacToe game
 * 
 * @author Sebastian Sonne
 * @version v2 23.03.2024
 */
public class Setup {
    private static final int UNIT = 100;
    private static final int GAP = 2;
    private static final int COL_START = Panel.SCREEN_WIDTH / 5;
    private static final int ROW_START = Panel.SCREEN_HEIGHT / 5;

    private static JButton[][] buttons;

    private static JButton resetButton;
    private static JButton singlePlayerButton;
    private static JButton multiPlayerButton;

    private static JLabel copyrightLabel;
    private static JLabel titleLabel;
    private static JLabel descriptionLabel;
    private static JLabel gameBoard;

    /**
     * function to set up function buttons
     * 
     * @param panel              JPanel to which buttons are added
     * @param resetAction        action which resetButton is executing
     * @param singlePlayerAction action which singlePlayerButton is executing
     * @param multiPlayerAction  action which multiPlayerButton is executing
     */
    public static void functionButtons(Panel panel, ActionListener resetAction, ActionListener singlePlayerAction,
            ActionListener multiPlayerAction) {

        resetButton = new JButton("Reset");
        singlePlayerButton = new JButton("1 Player");
        multiPlayerButton = new JButton("2 Players");

        JButton[] buttons = { resetButton, singlePlayerButton, multiPlayerButton };
        for (JButton button : buttons) {
            button.setBorder(null);
            button.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
            button.setBackground(Color.darkGray);
            button.setForeground(Color.white);
            button.setFocusable(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.gray);
        resetButton.setFont(new Font("SANS_SERIF", Font.PLAIN, 16));

        resetButton.setBounds(5, Panel.SCREEN_HEIGHT - 50, 50, 20);
        singlePlayerButton.setBounds(25, 250, 200, 75);
        multiPlayerButton.setBounds(Panel.SCREEN_WIDTH - 25 - 200, 250, 200, 75);

        resetButton.setVisible(false);
        singlePlayerButton.setVisible(true);
        multiPlayerButton.setVisible(true);

        resetButton.addActionListener(resetAction);
        singlePlayerButton.addActionListener(singlePlayerAction);
        multiPlayerButton.addActionListener(multiPlayerAction);

        panel.add(resetButton);
        panel.add(singlePlayerButton);
        panel.add(multiPlayerButton);
    }

    /**
     * function to setup game buttons
     * 
     * @param panel        JPanel to which buttons are added
     * @param buttonAction Action that buttons perform
     */
    public static void gameButtons(JPanel panel, ActionListener buttonAction) {
        buttons = new JButton[3][3];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j] = new JButton();

                buttons[i][j].setBorder(null);
                buttons[i][j].setFont(new Font("SANS_SERIF", Font.PLAIN, 96));
                buttons[i][j].setBackground(Color.black);
                buttons[i][j].setForeground(Color.white);
                buttons[i][j].setFocusable(false);
                buttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));

                int x = COL_START + UNIT * i;
                int y = ROW_START + UNIT * j;
                buttons[i][j].setBounds(x + GAP, y + GAP, UNIT - GAP, UNIT - GAP);

                buttons[i][j].addActionListener(buttonAction);
                buttons[i][j].setActionCommand(i + ":" + j);

                panel.add(buttons[i][j]);
            }
        }
    }

    /**
     * function to set up the copyright Label
     * 
     * @param panel JPanel to which the Label is added
     */
    public static void copyrightLabel(JPanel panel) {
        copyrightLabel = new JLabel("Copyright \u00A9 2024 Sebastian Sonne");

        copyrightLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 12));
        copyrightLabel.setForeground(Color.gray);
        copyrightLabel.setBounds(10, 475, 200, 15);
        copyrightLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyrightLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openURL("https://github.com/sebastian-sonne/ticTacToe");
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // not relevent
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                // not relevent
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // not relevent
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // not relevent
            }

        });

        panel.add(copyrightLabel);
    }

    /**
     * function to setup the titel JLabel
     * 
     * @param panel JPanel to which the Label is added
     */
    public static void titleLabel(JPanel panel) {
        titleLabel = new JLabel("Tic Tac Toe");

        titleLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 48));
        titleLabel.setBounds(130, 30, 300, 50);
        titleLabel.setForeground(Color.white);
        titleLabel.setBackground(Color.blue);

        panel.add(titleLabel);
    }

    /**
     * function to set up description Label
     * 
     * @param panel JPanel to which the Label is added
     */
    public static void descriptionLabel(JPanel panel) {
        descriptionLabel = new JLabel("Chose Game Mode");

        descriptionLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 22));
        descriptionLabel.setBounds(150, 200, 200, 20);
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setBackground(Color.cyan);

        panel.add(descriptionLabel);
    }

    /**
     * function to set up game Board
     * 
     * @param panel JPanel to which board is added
     */
    public static void gameBoard(JPanel panel) {
        try {
            BufferedImage gameBoardImg = ImageIO.read(new File("src/lib/gameBoard.png"));
            gameBoard = new JLabel(new ImageIcon(gameBoardImg));
            gameBoard.setBounds(102, 102, 296, 296);

            panel.add(gameBoard);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading game board image: " + e.getMessage(), "IO Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * function to open a url in the default browser
     * 
     * @param url url to be opend
     */
    private static void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * get & set methods
     */

    public static JButton getResetButton() {
        return resetButton;
    }

    public static JButton getSinglePlayerButton() {
        return singlePlayerButton;
    }

    public static JButton getMultiPlayerButton() {
        return multiPlayerButton;
    }

    public static JButton[][] getGameButtons() {
        return buttons;
    }

    public static JLabel getCopyrightLabel() {
        return copyrightLabel;
    }

    public static JLabel getTitleLabel() {
        return titleLabel;
    }

    public static JLabel getDescriptionLabel() {
        return descriptionLabel;
    }

    public static JLabel getGameBoard() {
        return gameBoard;
    }
}
