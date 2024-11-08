/**
 * Represents a single Tic tac toe game.
 */
public class Game {
    private static final int DEFAULT_BOARD_SIZE = 4;
    private static final int MIN_BOARD_SIZE = 2;
    private static final int DEFAULT_WIN_STREAK = 3;
    private final Player playerX;
    private final Player playerO;
    private final int boardSize;
    private int winStreak;
    private final Renderer renderer;

    /**
     * Constructor.
     * Constructs a game with default size and winStreak.
     * @param playerX player with X mark.
     * @param playerO player with O Mark.
     * @param renderer to renderer the board with.
     */
    public Game(Player playerX, Player playerO, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.boardSize = DEFAULT_BOARD_SIZE;
        this.winStreak = DEFAULT_WIN_STREAK;
        this.renderer = renderer;
    }

    /**
     * Constructor.
     * @param playerX player with X mark.
     * @param playerO player with O mark.
     * @param size of the game board.
     * @param winStreak of the game.
     * @param renderer to renderer the board with.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.boardSize = size;
        this.winStreak = winStreak;
        if(this.winStreak < MIN_BOARD_SIZE || this.winStreak > size){
            this.winStreak = this.boardSize;
        }
        this.renderer = renderer;
    }

    /**
     * @return the winStreak of the game.
     */
    public int getWinStreak(){
        return this.winStreak;
    }

    /**
     * @return the game board size.
     */
    public int getBoardSize(){
        return this.boardSize;
    }

    private Mark wonMark(int markCount1, int markCount2, Mark prevMark1, Mark prevMark2){
        if (markCount1 == winStreak){
            return prevMark1;
        }
        if (markCount2 == winStreak){
            return prevMark2;
        }
        return Mark.BLANK;
    }

    private Mark checkWinOverRowsAndCols(int j, Board board){
        Mark curMarkRow, curMarkCol;
        Mark prevMarkRow = Mark.BLANK, prevMarkCol = Mark.BLANK;
        int markCountRow = 0, markCountCol = 0;
        for(int k = 0; k < boardSize && markCountRow < winStreak && markCountCol < winStreak; k++){
            curMarkRow = board.getMark(j, k);
            curMarkCol = board.getMark(k, j);
            markCountRow = updateMarkCount(markCountRow, prevMarkRow, curMarkRow);
            markCountCol = updateMarkCount(markCountCol, prevMarkCol, curMarkCol);
            prevMarkRow = curMarkRow;
            prevMarkCol = curMarkCol;
        }
        return wonMark(markCountRow, markCountCol, prevMarkRow, prevMarkCol);
    }

    /**
     * Checks winner over "\" diagonals.
     */
    private Mark checkWinOverDiagonals(int i, Board board){
        Mark prevMark1 = Mark.BLANK, prevMark2 = Mark.BLANK, curMark1, curMark2;
        int markCount1 = 0, markCount2 = 0, j = 0;
        while (i < boardSize && j < boardSize && markCount1 < winStreak && markCount2 < winStreak){
            curMark1 = board.getMark(i, j);
            curMark2 = board.getMark(j, i);
            markCount1 = updateMarkCount(markCount1, prevMark1, curMark1);
            markCount2 = updateMarkCount(markCount2, prevMark2, curMark2);
            prevMark1 = curMark1;
            prevMark2 = curMark2;
            i++;
            j++;
        }
        return wonMark(markCount1, markCount2, prevMark1, prevMark2);
    }

    /**
     * Checks winner over "/" diagonals.
     */
    private Mark checkWinOverAntiDiagonals(int i, Board board){
        Mark prevMark1 = Mark.BLANK, prevMark2 = Mark.BLANK;
        int markCount1 = 0, markCount2 = 0, j = 0;
        while (i > -1 && j < boardSize && markCount1 < winStreak && markCount2 < winStreak){
            Mark curMark1 = board.getMark(i, j);
            Mark curMark2 = board.getMark(boardSize-1-j, boardSize - 1 - i);
            markCount1 = updateMarkCount(markCount1, prevMark1, curMark1);
            markCount2 = updateMarkCount(markCount2, prevMark2, curMark2);
            prevMark1 = curMark1;
            prevMark2 = curMark2;
            i--;
            j++;
        }
        return wonMark(markCount1, markCount2, prevMark1, prevMark2);
    }

    private int updateMarkCount(int markCount, Mark prevMark, Mark curMark){
        if(curMark == Mark.BLANK){
            return 0;
        }
        if(curMark == prevMark){
            return markCount+1;
        }
        return 1;
    }

    private Mark isThereWinner(Board board){
        Mark winnerMark = Mark.BLANK;
        for(int i = 0; i < this.boardSize; i++){
            winnerMark = checkWinOverRowsAndCols(i, board);
            if (winnerMark != Mark.BLANK){
                return winnerMark;
            }
            winnerMark = checkWinOverAntiDiagonals(i, board);
            if (winnerMark != Mark.BLANK){
                return winnerMark;
            }
            winnerMark = checkWinOverDiagonals(i, board);
            if (winnerMark != Mark.BLANK){
                return winnerMark;
            }
        }
        return winnerMark;
    }

    private boolean isBoardFull(Board board){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                Mark curMark = board.getMark(i, j);
                if (curMark == Mark.BLANK){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Runs a single game.
     * @return mark of the winner if there is one, BLANK otherwise.
     */
    public Mark run(){
        Board board = new Board(this.boardSize);
        while(true){
            this.playerX.playTurn(board, Mark.X);
            this.renderer.renderBoard(board);
            // Checks if PlayerX won or there is a tie.
            if (isThereWinner(board) == Mark.X){
                return Mark.X;
            }
            if(isBoardFull(board)){
                return Mark.BLANK;
            }
            this.playerO.playTurn(board, Mark.O);
            this.renderer.renderBoard(board);
            // Checks if PlayerO won or there is a tie.
            if (isThereWinner(board) == Mark.O){
                return Mark.O;
            }
            if(isBoardFull(board)){
                return Mark.BLANK;
            }
        }
    }
}
