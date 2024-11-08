/**
 * Represents a Tic tac toe tournament.
 */
public class Tournament {

    private static final String HUMAN_PLAYER = "human";
    private static final String CLEVER_PLAYER = "clever";
    private static final String GENIUS_PLAYER = "genius";
    private static final String WHATEVER_PLAYER = "whatever";
    private static final String CONSOLE_RENDERER = "console";
    private static final String VOID_RENDERER = "none";

    private final int rounds;
    private final Renderer renderer;
    private final Player player1;
    private final Player player2;

    /**
     * Constructor.
     * @param rounds to play in this tournament.
     * @param renderer to renderer the board with.
     * @param player1 in the tournament.
     * @param player2 in the tournament.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2){
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    private void printAfterTournament(String Name1, String Name2, int player1Won, int ties){
        System.out.println("######### Results #########");
        System.out.println(String.format("Player 1, %s won: %d rounds", Name1, player1Won));
        System.out.println(String.format("Player 2, %s won: %d rounds", Name2, rounds - ties - player1Won));
        System.out.println(String.format("Ties: %d", ties));
    }

    /**
     * Runs a full tournament; rounds times games.
     * @param size of the board.
     * @param winStreak of the games.
     * @param playerName1 name of the first player.
     * @param playerName2 name of the second player.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2){
        Player playerX = player1;
        Player playerO = player2;
        int ties = 0, player1WonRounds = 0;
        for (int i = 0; i < rounds; i++) {
            Game game = new Game(playerX, playerO, size, winStreak, this.renderer);
            Mark mark = game.run();
            // Update the total result after each game.
            if ((mark == Mark.X && i % 2 == 0) || (mark == Mark.O && i % 2 == 1)){
                player1WonRounds++;
            } else if (mark == Mark.BLANK) {
                ties++;
            }
            Player temp = playerO;
            playerO = playerX;
            playerX = temp;
        }
        printAfterTournament(playerName1, playerName2, player1WonRounds, ties);
    }

    private static boolean checkInvalidPlayerName(String playerName){
        switch (playerName){
            case HUMAN_PLAYER, CLEVER_PLAYER, WHATEVER_PLAYER, GENIUS_PLAYER:
                return false;
            default:
                return true;
        }
    }

    private static boolean checkInvalidRendererName(String rendererName){
        switch (rendererName){
            case  CONSOLE_RENDERER, VOID_RENDERER:
                return false;
            default:
                return true;
        }
    }

    /**
     * The main method.
     * @param args of the tournament.
     */
    public static void main(String[] args) {
        String rendererName = args[3].toLowerCase();
        if (checkInvalidRendererName(rendererName)){
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        }
        String player1Name = args[4].toLowerCase();
        String player2Name = args[5].toLowerCase();
        if(checkInvalidPlayerName(player1Name) || checkInvalidPlayerName(player2Name)){
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        }
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        Player player1 = new PlayerFactory().buildPlayer(player1Name);
        Player player2 = new PlayerFactory().buildPlayer(player2Name);
        Renderer renderer = new RendererFactory().buildRenderer(rendererName, size);
        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, args[4], args[5]);
    }
}
