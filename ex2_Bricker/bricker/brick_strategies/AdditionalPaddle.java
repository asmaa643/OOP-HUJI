package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Objects;

/**
 * Represents the adding paddle strategy.
 */
public class AdditionalPaddle extends Paddle implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final Counter collisionTimes;

    /**
     * Construct a new paddle GameObject instance.
     *
     * @param topLeftCorner     Position of the object, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the object. Can be null, in which case
     *                          the GameObject will not be rendered.
     * @param inputListener to control the paddle.
     * @param windowDimensionsX the x value of the window dimension.
     * @param brickerGameManager the manager of the game.
     * @param collisionTimes counter to set for 4 when a new second paddle is added.
     */
    public AdditionalPaddle(Vector2 topLeftCorner,
                            Vector2 dimensions,
                            Renderable renderable,
                            UserInputListener inputListener,
                            float windowDimensionsX,
                            BrickerGameManager brickerGameManager,
                            Counter collisionTimes){
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensionsX);
        this.brickerGameManager = brickerGameManager;
        this.collisionTimes = collisionTimes;
    }

    /**
     * Adds a second paddle if there is only the main paddle in the game.
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.removeBrick((Brick) thisObj);

        if (collisionTimes.value() > 0) {
            return;
        }
        brickerGameManager.addSecondPaddle(this);
        collisionTimes.increaseBy(Constants.PADDLE_COLLISIONS_TIMES);

    }

    /**
     * Called on the first frame of a collision, counts the collision times when a ball(main/puck) collides
     * with it.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (Objects.equals(other.getTag(), Constants.MAIN_BALL_TAG) ||
                Objects.equals(other.getTag(), Constants.PUCK_TAG)){
            this.collisionTimes.decrement();
        }
    }

}
