package bricker.main;

/**
 * Game Constants class.
 */
public class Constants {
    /**
     * Default constructor.
     */
    public Constants(){}

    /**
     * The game title
     */
    public final static String TITLE = "Bricker";

    // Objects sizes and speeds

    /**
     * The ball speed.
     */
    public final static float BALL_SPEED = 150;
    /**
     * The heart speed.
     */
    public final static float HEART_SPEED = 100;
    /**
     * The game border width.
     */
    public final static int BORDER_WIDTH = 10;
    /**
     * The paddle width.
     */
    public final static int PADDLE_WIDTH = 100;
    /**
     * The paddle height.
     */
    public final static int PADDLE_HEIGHT = 15;
    /**
     * The brick height.
     */
    public final static int BRICK_HEIGHT = 15;
    /**
     * The ball size.
     */
    public final static int BALL_SIZE = 20;
    /**
     * The puck size.
     */
    public final static int PUCK_SIZE = 15;
    /**
     * The heart and numeric size.
     */
    public final static float DISPLAY_SIZE = 20;
    /**
     * The board height.
     */
    public final static int BOARD_HEIGHT = 500;
    /**
     * The board width.
     */
    public final static int BOARD_WIDTH = 700;
    /**
     * space between objects.
     */
    public final static int SPACE = 25;
    /**
     * space between bricks.
     */
    public final static int BRICKS_SPACE = 25;

    // Game constants.

    /**
     * The max tries for a player.
     */
    public final static int MAX_TRIES = 3;
    /**
     * The collision times of a paddle.
     */
    public final static int PADDLE_COLLISIONS_TIMES = 4;
    /**
     * The ball collisions for the camera strategy.
     */
    public final static int CAMERA_BALL_COLLISIONS = 4;
    /**
     * The max hearts for a game.
     */
    public final static int MAX_HEART_NUM = 4;


    // Tags of the objects.

    /**
     * Tag of the main ball.
     */
    public final static String MAIN_BALL_TAG = "main_ball";
    /**
     * Tag of a puck.
     */
    public final static String PUCK_TAG = "puck";
    /**
     * Tag of the main paddle.
     */
    public final static String MAIN_PADDLE_TAG = "main_paddle";
    /**
     * Tag of the second paddle.
     */
    public final static String SECOND_PADDLE_TAG = "second_paddle";
    /**
     * Tag of a heart.
     */
    public final static String HEART_TAG = "heart";


    // Start/reset game constants.
    /**
     * want to play again message.
     */
    public final static String PLAY_AGAIN_MSG = " Play again?";
    /**
     * loss message.
     */
    public final static String LOSE_MSG = "You lose!";
    /**
     * won message.
     */
    public final static String WIN_MSG = "You win!";


    // Paths
    /**
     * path of the assets.
     */
    private static final String ASSETS = "assets/";
    /**
     * path of the paddle image.
     */
    public final static String PADDLE_PATH = ASSETS + "paddle.png";
    /**
     * path of the ball image.
     */
    public final static String BALL_PATH = ASSETS + "ball.png";
    /**
     * path of the ball/puck sound.
     */
    public final static String BALL_SOUND = ASSETS + "blop_cut_silenced.wav";
    /**
     * path of the puck image.
     */
    public final static String PUCK_PATH = ASSETS + "mockBall.png";
    /**
     * path of the brick image.
     */
    public final static String BRICK_PATH = ASSETS + "brick.png";
    /**
     * path of the heart image.
     */
    public final static String HEART_PATH = ASSETS + "heart.png";
    /**
     * path of the background image.
     */
    public final static String BACKGROUND_PATH = ASSETS + "DARK_BG2_small.jpeg";

    // Strategies numbering.

    /**
     * The basic behavior strategy.
     */
    public final static int BASIC_BEHAVIOR = 0;
    /**
     * The 2 pucks behavior strategy.
     */
    public final static int PUCKS_BEHAVIOR = 1;
    /**
     * The 2nd paddle behavior strategy.
     */
    public final static int PADDLE_BEHAVIOR = 2;
    /**
     * The camera behavior strategy.
     */
    public final static int CAMERA_BEHAVIOR = 3;
    /**
     * The heart behavior strategy.
     */
    public final static int HEARTS_BEHAVIOR = 4;
    /**
     * The double behavior strategy.
     */
    public final static int DOUBLE_BEHAVIOR = 5;
}
