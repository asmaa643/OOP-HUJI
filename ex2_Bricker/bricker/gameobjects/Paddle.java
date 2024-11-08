package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents the paddle game object of the game.
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final float windowDimensionsX;

    /**
     * Construct a new paddle GameObject instance.
     *
     * @param topLeftCorner     Position of the object, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the object. Can be null, in which case
     *                          the GameObject will not be rendered.
     * @param inputListener user input Listener to take movement input of paddle.
     * @param windowDimensionsX the x value of the window dimension.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, float windowDimensionsX) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensionsX = windowDimensionsX;
    }

    /**
     * Should be called once per frame, takes the movement input of paddle from the user and updates it.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && getTopLeftCorner().x() > Vector2.ZERO.x()) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                (getTopLeftCorner().x() + getDimensions().x()) < windowDimensionsX) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
