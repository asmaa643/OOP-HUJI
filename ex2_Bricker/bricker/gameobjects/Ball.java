package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents the game ball.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private final Counter collisionCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionSound the sound of the ball collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,  Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter(0);
    }


    /**
     * Called on the first frame of a collision, set the velocity, plays the ball sound and increments the
     * counter.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        this.collisionCounter.increment();
    }

    /**
     * Collision counter getter.
     * @return collisionCounter value.
     */
    public int getCollisionCounter(){
        return collisionCounter.value();
    }
}
