package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Strategy factory.
 */
public class Strategy {

    private static final int MAX_DOUBLE_BEHAVIORS = 3;
    private final Counter paddleCounter;
    private final Counter cameraCounter;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final Renderable renderable;
    private final UserInputListener inputListener;
    private final float windowDimensionsX;
    private final WindowController windowController;
    private final Ball ball;
    private final BrickerGameManager brickerGameManager;
    private final Counter doubleCounter;

    /**
     *
     * @param topLeftCorner of the paddle
     * @param dimensions of the paddle
     * @param renderable of the paddle image
     * @param inputListener to control the paddle.
     * @param windowDimensionsX the x coordinate of the window.
     * @param windowController of the game.
     * @param ball the main ball game object.
     * @param brickerGameManager the manager of the game.
     * @param paddleCounter to count collisions and decide if it is possible to add a second paddle.
     * @param cameraCounter to count collisions and sets the camera back.
     */
    public Strategy(Vector2 topLeftCorner,
                    Vector2 dimensions,
                    Renderable renderable,
                    UserInputListener inputListener,
                    float windowDimensionsX,
                    WindowController windowController,
                    Ball ball, BrickerGameManager brickerGameManager,
                    Counter paddleCounter, Counter cameraCounter) {

        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.inputListener = inputListener;
        this.windowDimensionsX = windowDimensionsX;
        this.windowController = windowController;
        this.ball = ball;
        this.brickerGameManager = brickerGameManager;
        this.paddleCounter = paddleCounter;
        this.cameraCounter = cameraCounter;
        doubleCounter = new Counter(0);
    }


    /**
     * The Collision Strategy factory.
     * @param strategies to choose from.
     * @param probabilities of the strategies.
     * @param layerCounter to use for the double behavior, to limits the strategy numbers.
     * @return the strategy that was chosen.
     */
    public CollisionStrategy chooseACollideStrategy(int[] strategies,
                                                    double[] probabilities,
                                                    int layerCounter) {
        int chosenAction = chooseRandomStrategy(strategies, probabilities);
        switch (chosenAction) {
            case Constants.BASIC_BEHAVIOR:
                return new BasicCollisionStrategy(brickerGameManager);
            case Constants.PUCKS_BEHAVIOR:
                return new AdditionalBall(brickerGameManager);
            case Constants.PADDLE_BEHAVIOR:
                return new AdditionalPaddle(topLeftCorner, dimensions, renderable, inputListener,
                        windowDimensionsX, brickerGameManager, paddleCounter);
            case Constants.CAMERA_BEHAVIOR:
                return new CameraFocus(brickerGameManager, windowController, ball, cameraCounter);
            case Constants.HEARTS_BEHAVIOR:
                return new ReturnHearts(brickerGameManager);
            case Constants.DOUBLE_BEHAVIOR:
                return getDoubleBehavior(layerCounter);
            default:
                return null;
        }
    }

    /**
     * Chooses a strategy with probabilities.
     * @param strategies to choose from.
     * @param probabilities of the strategies.
     * @return the number of the strategy that was chosen.
     */
    private int chooseRandomStrategy(int[] strategies, double[] probabilities) {
        Random random = new Random();
        double rand = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < strategies.length; i++) {
            cumulativeProbability += probabilities[i];
            if (rand < cumulativeProbability) {
                return strategies[i];
            }
        }
        return strategies[strategies.length - 1];
    }


    /**
     * Implements the double strategy (limits the layers).
     */
    private DoubleBehavior getDoubleBehavior(int layerCounter) {
        doubleCounter.increment();
        if (doubleCounter.value() >= MAX_DOUBLE_BEHAVIORS) {
            return null;
        }
        CollisionStrategy collisionStrategy1, collisionStrategy2;
        int[] behaviors;
        double[] probs;
        if (layerCounter == 1) {
            behaviors = new int[]{Constants.PUCKS_BEHAVIOR, Constants.PADDLE_BEHAVIOR,
                    Constants.CAMERA_BEHAVIOR, Constants.HEARTS_BEHAVIOR};
            probs = new double[]{0.25, 0.25, 0.25, 0.25};
        } else {
            behaviors = new int[]{Constants.PUCKS_BEHAVIOR, Constants.PADDLE_BEHAVIOR,
                    Constants.CAMERA_BEHAVIOR, Constants.HEARTS_BEHAVIOR, Constants.DOUBLE_BEHAVIOR};
            probs = new double[]{0.2, 0.2, 0.2, 0.2, 0.2};
        }
        collisionStrategy1 = chooseACollideStrategy(behaviors, probs, layerCounter + 1);
        collisionStrategy2 = chooseACollideStrategy(behaviors, probs, layerCounter + 1);
        return new DoubleBehavior(collisionStrategy1, collisionStrategy2);
    }
}
