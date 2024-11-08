/**
 * Represents a player in the game.
 */
public interface Player {
    /**
     * Plays a turn for the player; chooses a cell to put mark on.
     * @param board to put mark on.
     * @param mark to put.
     */
    void playTurn(Board board, Mark mark);
}
