import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Play {
    //TODO move all function corresponding with gameplay here

    /**
     * function to show the game eval dialog
     * @param gameState final gameState evaluation: -1, 0, 1 possible
     * @param panel reference to JPanel to show dialog
     */
    public static void showWinner(int gameState, JPanel panel) {
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

        int choice = JOptionPane.showOptionDialog(panel, message, "Game Results", JOptionPane.YES_NO_CANCEL_OPTION,
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

    private static void resetGame() {
        System.out.println("Reset Game - implement!");

        //add getter methods in panel class for jbuttons[][]
    }
    
}
