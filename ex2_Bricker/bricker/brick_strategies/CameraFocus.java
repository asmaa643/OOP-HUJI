package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Objects;

/**
 * Represents setting the camera strategy.
 */
public class CameraFocus implements CollisionStrategy{

    private final BrickerGameManager brickerGameManager;
    private final WindowController windowController;
    private final Ball ball;
    private final Counter ballCounter;

    /**
     * The camera strategy constructor.
     * @param brickerGameManager the manager of the game.
     * @param windowController of the game
     * @param ball the main ball object of game.
     * @param ballCounter counter for the collisions of the ball with other objects.
     */
    public CameraFocus(BrickerGameManager brickerGameManager,
                       WindowController windowController,
                       Ball ball, Counter ballCounter){

        this.brickerGameManager = brickerGameManager;
        this.windowController = windowController;
        this.ball = ball;
        this.ballCounter = ballCounter;
    }

    /**
     * When collision it sets the camera to follow the main ball up to 4 collisions of the ball with other
     * objects in the game.
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.removeBrick((Brick) thisObj);
        if (Objects.equals(otherObj.getTag(), this.ball.getTag()) &&
                this.brickerGameManager.camera() == null){
            this.brickerGameManager.setCamera(
                    new Camera(this.ball, //object to follow
                            Vector2.ZERO, //follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                            windowController.getWindowDimensions() //share the window dimensions
                    ));
            this.ballCounter.reset();
            this.ballCounter.increaseBy(this.ball.getCollisionCounter());
        }
    }
}
