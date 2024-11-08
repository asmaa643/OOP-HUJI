/**
 * Represents a clever player who plays Tic tac toe.
 */
public class CleverPlayer implements Player{
    /**
     * Constructor.
     */
    public CleverPlayer(){}

    /**
     * Plays clever strategy: fills in columns the first empty cell.
     * @param board to put mark on.
     * @param mark to put.
     */
    public void playTurn(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getMark(j, i) == Mark.BLANK) {
                    board.putMark(mark, j, i);
                    return;
                }
            }
        }
    }
}
