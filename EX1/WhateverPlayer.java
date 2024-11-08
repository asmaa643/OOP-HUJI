import java.util.Random;

/**
 * Represents a player who plays randomly.
 */
public class WhateverPlayer implements Player{
    /**
     * Constructor.
     */
    public WhateverPlayer(){}

    /**
     * Plays a turn of the whatever player, who chooses a cell randomly.
     * @param board to put mark on.
     * @param mark to put.
     */
    public void playTurn(Board board, Mark mark) {
        Random rand = new Random();
        int random1 = rand.nextInt(board.getSize());
        int random2 = rand.nextInt(board.getSize());
        while (board.getMark(random1, random2) != Mark.BLANK){
            random1 = rand.nextInt(board.getSize());
            random2 = rand.nextInt(board.getSize());
        }
        board.putMark(mark, random1, random2);
    }
}
