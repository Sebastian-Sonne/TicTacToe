import javax.swing.JButton;

public class Computer {

    /**
     * function to find whose players turn it is
     * 
     * @param buttons current game state
     * @return O, if Player O turn; X if Player X turn
     */
    public static String getPlayerTurn() {
        int moveNum = Panel.getMoveNum();
        return (moveNum % 2 == 0) ? "X" : "O";
    }

    /**
     * function to check whether a gamestate is terminal
     * 
     * @param buttons current gamestate
     * @return -1 -> O won; 1 -> X won; 0 -> its a tie; 10 -> game not over
     */
    public static int isTerminal(JButton[][] buttons, int moveNum) {
        // Check horizontal and vertical lines
        for (int i = 0; i < buttons.length; i++) {
            if (checkLine(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText()))
                return (buttons[i][0].getText().equals("O")) ? -1 : 1;

            if (checkLine(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText()))
                return (buttons[0][i].getText().equals("O")) ? -1 : 1;
        }

        // Check diagonals
        if (checkLine(buttons[0][0].getText(), buttons[1][1].getText(), buttons[2][2].getText())
                || checkLine(buttons[0][2].getText(), buttons[1][1].getText(), buttons[2][0].getText()))
            return (buttons[1][1].getText().equals("O")) ? -1 : 1;

        // check for tie
        if (moveNum == 9)
            return 0;

        return 10;
    }

    /**
     * function to check wheter a three Strings are equal and not empty
     * 
     * @param a first String
     * @param b second String
     * @param c third String
     * @return true if equal & not empty
     */
    private static boolean checkLine(String a, String b, String c) {
        return !a.isEmpty() && a.equals(b) && a.equals(c);
    }

    /**
     * function to find all possible moves in a current gameState
     * 
     * @param buttons current gameState
     * @return row & col of each possible move, as a 2d array
     */
    private static int[][] findMoves(JButton[][] buttons) {
        int emptyCount = 0;

        // Count the number of empty buttons
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().equals("")) {
                    emptyCount++;
                }
            }
        }

        // Create result array based on the number of empty buttons
        int[][] res = new int[emptyCount][2];
        int index = 0;

        // Populate the result array with coordinates of empty buttons
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().equals("")) {
                    res[index][0] = i;
                    res[index][1] = j;
                    index++;
                }
            }
        }

        return res;
    }

    /**
     * function to merge a move into a copy of the gameState
     * @param gameState copy of the current gamestate
     * @param row row of next move
     * @param col column of next move
     * @return new gamestate
     */
    private static String[][] mergeMove(String[][] gameState, int row, int col) {
        gameState[row][col] = getPlayerTurn();

        return gameState;
    }

    /**
     * function to create a working 2d String array copy of 2d JButton array 
     * @param buttons current gamestate
     * @return 2d String array of gamestate
     */
    public static String[][] copyToStringArray(JButton[][] buttons) {
        String[][] gameState = new String[buttons.length][buttons[0].length];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                gameState[i][j] = buttons[i][j].getText();
            }
        }

        return gameState;
    }

    public static void miniMax() {
        //TODO implement function
    }
}
