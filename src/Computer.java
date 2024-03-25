import javax.swing.JButton;

/**
 * Class to calculate the next best move for single Player mdoe
 * 
 * @author Sebastian Sonne
 * @version v1 20.03.2024
 */
public class Computer {

    /**
     * function to get player turn based on move Num
     * 
     * @param buttons current game state
     * @return O, if Player O turn; X if Player X turn
     */
    public static String getPlayerTurn() {
        int moveNum = Panel.getMoveNum();
        return (moveNum % 2 == 0) ? "X" : "O";
    }

    /**
     * function to get player turn based on gameState
     * @param gameState current gameState
     * @return "O" or "X"
     */
    private static String getPlayerTurn(String[][] gameState) {
        int countO = 0, countX = 0;

        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (gameState[i][j].equals("O")) {
                    countO++;
                } else if (gameState[i][j].equals("X")) {
                    countX++;
                }
            }
        }
        return (countX > countO) ? "O" : "X";
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
     * 
     * @param gameState copy of the current gamestate
     * @param row       row of next move
     * @param col       column of next move
     * @return new gamestate
     */
    private static String[][] mergeMove(String[][] gameState, int row, int col) {
        gameState[row][col] = getPlayerTurn();

        return gameState;
    }

    /**
     * function to create a working 2d String array copy of 2d JButton array
     * 
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

    /**
     * function to find the move Number based on the gameState
     * 
     * @param gameState
     * @return moveNumber
     */
    private static int getMoveNum(String[][] gameState) {
        int count = 0;

        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (!gameState[i][j].equals("-"))
                    count++;
            }
        }
        return count;
    }

    /**
     * function to identify the next best move
     * 
     * @param gameState current gameState as String[][]
     * @return int[] = {evalValue, row, col}
     */
    public static int[] miniMax(String[][] gameState) {
        String playerTurn = getPlayerTurn(gameState);
        int[] evaluation = { 10, -1, -1 }; // [0] = score, [1] = row, [2] = col

        if (playerTurn.equals("O")) {
            return minMove(gameState, evaluation);
        } else {
            return maxMove(gameState, evaluation);
        }
    }

    public static void toString(String[][] gameState) {
        for (String[] game : gameState) {
            for (String let : game) {
                if (let.equals("")) {
                    System.out.print("  -  ");
                } else {
                    System.out.print(let + " | ");
                }
            }
            System.out.println();
        }
    }

    public static void toString(JButton[][] gameState) {
        for (JButton[] game : gameState) {
            for (JButton let : game) {
                if (let.getText().equals("")) {
                    System.out.print("  -  ");
                } else {
                    System.out.print(let.getActionCommand() + " | ");
                }
            }
            System.out.println();
        }
    }

    /**
     * function to find the next best move for the maximizing player
     * 
     * @param gameState  current gameState String[][]
     * @param evaluation current eveluation array: int[] = {evalValue, row, col}
     * @return new eveluation array: int[] = {evalValue, row, col}
     */
    private static int[] maxMove(String[][] gameState, int[] evaluation) {
        evaluation[0] = Integer.MIN_VALUE;
        int[][] moves = findMoves(gameState); // example {{2, 3}, {1, 2}}

        for (int[] move : moves) {
            String[][] newGameState = mergeMove(gameState, move[0], move[1]);
            int newGameStateEval = eveluateGameState(newGameState);

            if (newGameStateEval > evaluation[0]) {
                evaluation[0] = newGameStateEval;
                evaluation[1] = move[0];
                evaluation[2] = move[1];
            }

        }

        return evaluation;
    }

    /**
     * function to find the next best move for the minimizing player
     * 
     * @param gameState  current gameState String[][]
     * @param evaluation current eveluation array: int[] = {evalValue, row, col}
     * @return new eveluation array: int[] = {evalValue, row, col}
     */
    private static int[] minMove(String[][] gameState, int[] evaluation) {
        evaluation[0] = Integer.MAX_VALUE;
        int[][] moves = findMoves(gameState); // example {{2, 3}, {1, 2}}

        for (int[] move : moves) {
            String[][] newGameState = mergeMove(gameState, move[0], move[1]);
            int newGameStateEval = eveluateGameState(newGameState);

            if (newGameStateEval < evaluation[0]) {
                evaluation[0] = newGameStateEval;
                evaluation[1] = move[0];
                evaluation[2] = move[1];
            }
        }

        return evaluation;
    }

    /**
     * function to eveluate a gameState
     * @param gameState current GameState
     * @return eveluation score
     */
    private static int eveluateGameState(String[][] gameState) {
        int moveNum = getMoveNum(gameState);
        int gameEvaluation = isTerminal(gameState, moveNum);

        if (gameEvaluation == -1 || gameEvaluation == 0 || gameEvaluation == 1) {
            //System.out.println("log");
            return gameEvaluation;
        } else {
            int[] eveluation = miniMax(gameState);
            //System.out.println("log 1");
            return eveluation[0];
        }
    }
}
