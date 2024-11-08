package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a single game brick.
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionStrategy the strategy to use when a ball collides with this brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Called on the first frame of a collision, call the collisionStrategy of the brick.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }
}
