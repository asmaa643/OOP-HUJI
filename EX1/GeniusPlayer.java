/**
 * Represents a genius player who plays Tic tac toe.
 * He wins always when playing with clever.
 */
public class GeniusPlayer implements Player {
    /**
     * Constructor.
     */
    public GeniusPlayer() {
    }

    /**
     * Plays a turn to the genius player.
     * @param board to put mark on.
     * @param mark to put.
     */
    public void playTurn(Board board, Mark mark) {
        if (preventCleverFromWinning(board, mark)){
            return;
        }
        chooseMarkCell(board, mark);
    }

    private boolean preventCleverFromWinning(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            int player2ColsCellsCounter = 0;
            for (int j = 0; j < board.getSize(); j++) {
                Mark cellMark = board.getMark(j, i);
                if (cellMark != Mark.BLANK && cellMark != mark) {
                    player2ColsCellsCounter++;
                    if (player2ColsCellsCounter == 2) {
                        if (board.putMark(mark, j + 1, i)) {
                            return true;
                        }
                    }
                } else if (cellMark == Mark.BLANK) {
                    return false;
                } else {
                    player2ColsCellsCounter = 0;
                }
            }
        }
        return false;
    }

    private void chooseMarkCell(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.putMark(mark, board.getSize() - i - 1, i)) {
                return;
            }
        }
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.putMark(mark, j, i)) {
                    return;
                }
            }
        }
    }
}
