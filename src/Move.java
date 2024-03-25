/**
 * Class to store Move Data for MiniMax algorithm
 *
 * @author Sebastian Sonne
 * @version v1 25.03.2024
 */
public class Move {
    public int evaluation;
    public int row, col;

    /**
     * constructor of Move class
     * @param evaluation
     * @param row
     * @param col
     */
    public Move(int evaluation, int row, int col) {
        this.evaluation = evaluation;
        this.row = row;
        this.col = col;
    }
}
