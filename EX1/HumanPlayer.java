/**
 * Represents a human player.
 * Takes input from user to play.
 */
public class HumanPlayer implements Player{

    /**
     * Constructor.
     */
    public HumanPlayer(){

    }

    /**
     * Plays turn for human; takes the cell as input from user and updates it on the board.
     * @param board to put mark on.
     * @param mark to put.
     */
    public void playTurn(Board board, Mark mark) {
        System.out.println(String.format(Constants.playerRequestInputString(mark.name())));

        while (true){
            int input = KeyboardInput.readInt();
            int row = input / 10;
            int col = input % 10;
            if(board.getMark(row, col) == Mark.BLANK){
                if(!board.putMark(mark, row, col)){
                    System.out.println(Constants.INVALID_COORDINATE);
                    continue;
                }
                break;
            }
            System.out.println(Constants.OCCUPIED_COORDINATE);
        }
    }
}
