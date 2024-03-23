import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

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

    /**
     * function to set up the function buttons
     * 
     * @param resetButton        reset JButton
     * @param singlePlayerButton singlePlayer JButton
     * @param multiPlayerButton  multiPlayer JButton
     * @param resetAction        action of reset JButton
     * @param singlePlayerAction action of singlePlayer JButton
     * @param multiPlayerAction  action of multiPlayer JButton
     */
    public static void functionButtons(JButton resetButton, JButton singlePlayerButton, JButton multiPlayerButton,
            ActionListener resetAction, ActionListener singlePlayerAction,
            ActionListener multiPlayerAction) {
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
    }

    /**
     * function to set up a game button
     * @param button JButton
     * @param row row of JButton
     * @param col col of JButton
     * @param buttonAction action of JButton
     */
    public static void gameButtons(JButton button, int row, int col, ActionListener buttonAction) {
        button.setBorder(null);
        button.setFont(new Font("SANS_SERIF", Font.PLAIN, 96));
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        int x = COL_START + UNIT * row;
        int y = ROW_START + UNIT * col;
        button.setBounds(x + GAP, y + GAP, UNIT - GAP, UNIT - GAP);

        button.addActionListener(buttonAction);
        button.setActionCommand(row + ":" + col);
    }

    /**
     * function to set up copyright JLabel
     * @param copyrightLabel (c) Label
     */
    public static void copyright(JLabel copyrightLabel) {
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
    }

    /**
     * function to setup the titel JLabel
     * @param titleLabel title JLabel
     * @param x x-coordinate of JLabel
     * @param y y-ccordinate of JLabel
     */
    public static void title(JLabel titleLabel, int x, int y) {
        titleLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 48));
        titleLabel.setBounds(x, y, 300, 50);
        titleLabel.setForeground(Color.white);
        titleLabel.setBackground(Color.blue);
    }

    /**
     * function to setup the description JLabel
     * @param instructionLabel description JLabel
     * @param x x-coordinate of JLabel
     * @param y y-ccordinate of JLabel
     */
    public static void description(JLabel instructionLabel, int x, int y) {
        instructionLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 22));
        instructionLabel.setBounds(x, y, 200, 20);
        instructionLabel.setForeground(Color.white);
        instructionLabel.setBackground(Color.cyan);
    }

    /**
     * function to set up the gameBoard image
     * @return gameBoard image
     * @throws IOException 
     */
    public static JLabel gameBoard() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("src/lib/gameBoard.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(102, 102, 296, 296);
        return picLabel;
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
}
