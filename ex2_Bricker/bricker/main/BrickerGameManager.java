package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;

/**
 * The bricker game manager, the main class.
 */
public class BrickerGameManager extends GameManager {
    private static int bricksRows = 7;
    private static int bricksNumber = 8;
    private static int totalBricks;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageRenderable heartImage;
    private GraphicDisplay graphicDisplay;
    private static int bricksCounter;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Paddle userPaddle;
    private Counter paddleCounter;
    private Counter cameraCounter;


    /**
     * Constructs the bricker game manager.
     * @param windowTitle the bricker game title.
     * @param windowDimensions of the game screen.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    /**
     * The method will be called once when a GameGUIComponent is created,
     * and again after every invocation of windowController.resetGame().
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        bricksCounter = totalBricks;
        this.paddleCounter = new Counter(0);
        this.cameraCounter = new Counter(0);
        // background
        setBackground(imageReader);
        // Ball
        addBallObject(imageReader, soundReader);
        // Borders
        addBorders(this.windowDimensions);
        // Paddle
        addUserPaddle(imageReader, inputListener, this.windowDimensions);
        // Bricks
        addBricks(imageReader, this.windowDimensions);
        // Hearts and Losses
        resultDisplay(imageReader);
    }


    private void setBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage(Constants.BACKGROUND_PATH,
                false);
        GameObject background = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), windowDimensions.y()), backgroundImage);
        background.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    private void addBallObject(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(Constants.BALL_PATH, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND);
        this.ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_SIZE, Constants.BALL_SIZE),
                ballImage, collisionSound);
        centerTheBall();
        this.ball.setTag(Constants.MAIN_BALL_TAG);
        this.gameObjects().addGameObject(this.ball);
    }

    private void centerTheBall() {
        float ballVelX = Constants.BALL_SPEED, ballVelY = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        this.ball.setVelocity(new Vector2(ballVelX, ballVelY));
        this.ball.setCenter(windowDimensions.mult(0.5F));
    }

    private void addBorders(Vector2 windowDimensions) {
        GameObject leftBorder = new GameObject(Vector2.ZERO, new Vector2(Constants.BORDER_WIDTH,
                windowDimensions.y()), null);
        gameObjects().addGameObject(leftBorder);
        GameObject rightBorder =
                new GameObject(Vector2.RIGHT.mult(windowDimensions.x() - Constants.BORDER_WIDTH),
                        new Vector2(Constants.BORDER_WIDTH, windowDimensions.y() * 2), null);
        gameObjects().addGameObject(rightBorder);
        GameObject upperBorder = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                (float) Constants.BORDER_WIDTH), null);
        gameObjects().addGameObject(upperBorder);
    }

    private void addUserPaddle(ImageReader imageReader, UserInputListener inputListener,
                               Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_PATH, true);
        this.userPaddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT), paddleImage, inputListener, windowDimensions.x());
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        this.userPaddle.setTag(Constants.MAIN_PADDLE_TAG);
        gameObjects().addGameObject(userPaddle);
    }

    private void addBricks(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_PATH, true);
        Renderable brickImage = imageReader.readImage(Constants.BRICK_PATH, false);
        for (int i = 0; i < bricksRows; i++) {
            int initilize = Constants.SPACE;
            int width = ((int) ((windowDimensions.x() - (initilize * 2)) / (bricksNumber))) - bricksNumber;
            for (int j = 0; j < bricksNumber; j++) {
                Strategy strategy = new Strategy(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                        Constants.PADDLE_HEIGHT), paddleImage, inputListener, windowDimensions.x(),
                        windowController, this.ball, this, this.paddleCounter,
                        this.cameraCounter);
                int[] behaviors = new int[]{Constants.BASIC_BEHAVIOR, Constants.PUCKS_BEHAVIOR,
                        Constants.PADDLE_BEHAVIOR, Constants.CAMERA_BEHAVIOR, Constants.HEARTS_BEHAVIOR,
                        Constants.DOUBLE_BEHAVIOR};
                double[] probabilities = new double[]{0.5, 0.1, 0.1, 0.1, 0.1, 0.1};
                Brick brick = new Brick(new Vector2(initilize,
                        Constants.SPACE + i * Constants.BRICKS_SPACE),
                        new Vector2(width, Constants.BRICK_HEIGHT), brickImage,
                        strategy.chooseACollideStrategy(behaviors, probabilities, 0));
                gameObjects().addGameObject(brick);
                initilize += width + 10;
            }
        }
    }

    private void resultDisplay(ImageReader imageReader) {
        Counter triesCounter = new Counter(Constants.MAX_TRIES);
        TextRenderable textRenderable = new TextRenderable(Integer.toString(Constants.MAX_TRIES));
        this.heartImage = imageReader.readImage(Constants.HEART_PATH, true);
        this.graphicDisplay = new GraphicDisplay(heartImage, gameObjects(), userPaddle,
                textRenderable, triesCounter);
    }



    /**
     * Called once per frame. Any logic is put here. Rendering, on the other hand,
     * should only be done within 'render'.
     * Note that the time that passes between subsequent calls to this method is not constant.
     *
     * Checks win and loss, remove fallen out objects.
     * If the camera is turned on, it sets it back if the ball collisions is 4.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (inputListener.isKeyPressed(KeyEvent.VK_W)) {
            String prompt = Constants.WIN_MSG;
            wantToPlayAgain(prompt);
        }
        if (ball.getCollisionCounter() - this.cameraCounter.value() >= Constants.CAMERA_BALL_COLLISIONS) {
            this.setCamera(null);
        }
        removeOutObjects();
        checkWin();
        checkLoss();
    }

    private void removeOutObjects() {
        for (GameObject obj : this.gameObjects()) {
            if ((Objects.equals(obj.getTag(), Constants.PUCK_TAG) || Objects.equals(obj.getTag(),
                    Constants.HEART_TAG)) && obj.getTopLeftCorner().y() > Constants.BOARD_HEIGHT) {
                this.gameObjects().removeGameObject(obj);
            } else if (Objects.equals(obj.getTag(), Constants.SECOND_PADDLE_TAG) &&
                    this.paddleCounter.value() == 0) {
                this.gameObjects().removeGameObject(obj);
            }
        }
    }

    private void checkWin() {
        String prompt = "";
        if (bricksCounter <= 0) {
            prompt = Constants.WIN_MSG;
            wantToPlayAgain(prompt);
        }
    }

    private void checkLoss() {
        String prompt = "";
        float ballHeight = ball.getCenter().y();
        if (ballHeight > Constants.BOARD_HEIGHT) {
            prompt = Constants.LOSE_MSG;
            this.graphicDisplay.removeHeart();
        }
        isGameOverLoss(prompt);
    }

    private void isGameOverLoss(String prompt) {
        if (this.graphicDisplay.getTries() == 0) {
            wantToPlayAgain(prompt);
        } else if (!prompt.isEmpty()) {
            centerTheBall();
        }
    }

    private void wantToPlayAgain(String prompt) {
        prompt += Constants.PLAY_AGAIN_MSG;
        if (this.windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }


    /**
     * Adds a second paddle when Additional Paddle strategy occurs.
     * @param additionalPaddle the second paddle to add.
     */
    public void addSecondPaddle(AdditionalPaddle additionalPaddle) {
        additionalPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
        additionalPaddle.setTag(Constants.SECOND_PADDLE_TAG);
        gameObjects().addGameObject(additionalPaddle);
    }

    /**
     * Adds a single puck when Additional ball strategy occurs, it is called twice, once for each puck.
     */
    public void addAdditionalBallObject() {
        Renderable ballImage = imageReader.readImage(Constants.PUCK_PATH, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND);
        Ball addBall = new Ball(Vector2.ZERO,
                new Vector2(Constants.PUCK_SIZE, Constants.PUCK_SIZE),
                ballImage, collisionSound);
        Random rand = new Random();
        double angle = rand.nextFloat() * Math.PI;
        float ballVelX = Constants.BALL_SPEED * (float) Math.cos(angle);
        float ballVelY = Constants.BALL_SPEED * (float) Math.sin(angle);
        addBall.setVelocity(new Vector2(ballVelX, ballVelY));
        addBall.setCenter(windowDimensions.mult(0.5F));
        addBall.setTag(Constants.PUCK_TAG);
        this.gameObjects().addGameObject(addBall);
    }


    /**
     * Adds a single heart when Return heart strategy occurs.
     * @param gameObject1 the brick that was broken, to take its place and center tha fallen heart there.
     */
    public void addHeart(GameObject gameObject1) {
        Heart heart = new Heart(Vector2.ZERO,
                new Vector2(Constants.DISPLAY_SIZE, Constants.DISPLAY_SIZE),
                heartImage, this.graphicDisplay, this.userPaddle);
        heart.setCenter(gameObject1.getCenter());
        heart.setVelocity(Vector2.DOWN.mult(Constants.HEART_SPEED));
        heart.setTag(Constants.HEART_TAG);
        this.gameObjects().addGameObject(heart);
    }


    /**
     * A method that removes a brick from the game, if it wasn't removed, decrements the bricks counter.
     * @param brick to remove.
     */
    public void removeBrick(Brick brick) {
        if (this.gameObjects().removeGameObject(brick)) {
            bricksCounter--;
        }
    }


    /**
     * The main method that starts a game.
     * @param args of the game, args[0] is the bricks number and args[1] is the bricks rows.
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            bricksNumber = Integer.parseInt(args[0]);
            bricksRows = Integer.parseInt(args[1]);
        }
        totalBricks = bricksNumber * bricksRows;
        Vector2 boardCord = new Vector2(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        new BrickerGameManager(Constants.TITLE, boardCord).run();
    }


}
