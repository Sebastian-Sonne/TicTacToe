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
     * @param gameState current gamestate
     * @return -1 -> O won; 1 -> X won; 0 -> its a tie; 10 -> game not over
     */
    public static int isTerminal(String[][] gameState, int moveNum) {
        // Check horizontal and vertical lines
        for (int i = 0; i < gameState.length; i++) {
            if (checkLine(gameState[i][0], gameState[i][1], gameState[i][2]))
                return (gameState[i][0].equals("O")) ? -1 : 1;

            if (checkLine(gameState[0][i], gameState[1][i], gameState[2][i]))
                return (gameState[0][i].equals("O")) ? -1 : 1;
        }

        // Check diagonals
        if (checkLine(gameState[0][0], gameState[1][1], gameState[2][2])
                || checkLine(gameState[0][2], gameState[1][1], gameState[2][0]))
            return (gameState[1][1].equals("O")) ? -1 : 1;

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
     * @param gameState current gameState
     * @return row & col of each possible move, as a 2d array
     */
    private static int[][] findMoves(String[][] gameState) {
        int emptyCount = 0;

        // Count the number of possible placesments
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (gameState[i][j].equals("")) {
                    emptyCount++;
                }
            }
        }

        // Create result array based on the number of possible placements
        int[][] res = new int[emptyCount][2];
        int index = 0;

        // Populate the result array with coordinates of possible placements
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (gameState[i][j].equals("")) {
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

    public static void miniMax(String[][] gameState, String playerTurn) {
        //TODO implement function
    }
}
