/**
 * Represents the board in the game Tic tac toe.
 * Marks can be added to the board within its size.
 */
public class Board {

    private static final int DEFAULT_BOARD_SIZE = 4;

    private final int size;
    private final Mark[] board;

    /**
     * Default constructor.
     * Initializes a 4x4 board.
     */
    public Board(){
        this.size = DEFAULT_BOARD_SIZE;
        this.board = new Mark[size*size];
        fillBlankBoard();
    }

    /**
     * Constructor.
     * @param size to initialize the board with.
     */
    public Board(int size){
        this.size = size;
        this.board = new Mark[size*size];
        fillBlankBoard();
    }

    private void fillBlankBoard(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i*this.size+j] = Mark.BLANK;
            }
        }
    }

    /**
     * @return the board size.
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Puts a mark on the row,col cell.
     * @param mark to put.
     * @param row to put the mark on, first coordinate.
     * @param col to put the mark on, second coordinate.
     * @return true when putting the mark succeeded and false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col){
        if (row >= size || col >= size || row < 0 || col < 0){
            return false;
        }
        if (this.board[row*this.size+col] == Mark.BLANK){
            this.board[row*this.size+col] = mark;
            return true;
        }
        return false;
    }

    /**
     * @param row first coordinate.
     * @param col second coordinate.
     * @return which mark is on the given cell, if the cell is outside the board return BLANK mark.
     */
    public Mark getMark(int row, int col){
        if (row >= size || col >= size || row < 0 || col < 0){
            return Mark.BLANK;
        }
        return this.board[row*this.size+col];
    }
}
