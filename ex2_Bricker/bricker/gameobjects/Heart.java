package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Objects;

/**
 * Represents a single heart game object.
 */
public class Heart extends GameObject {
    private final GraphicDisplay graphicDisplay;
    private final Paddle mainPaddle;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param graphicDisplay the graphic display of the game.
     * @param mainPaddle the user main paddle.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable,
                 GraphicDisplay graphicDisplay,
                 Paddle mainPaddle) {
        super(topLeftCorner, dimensions, renderable);
        this.graphicDisplay = graphicDisplay;
        this.mainPaddle = mainPaddle;
    }


    /**
     * Called on the first frame of a collision, sets its speed to zero and adds it to the game display.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        this.setVelocity(Vector2.ZERO);
        this.graphicDisplay.collectHeart(this);
    }

    /**
     * Should this object be allowed to collide the specified other object.
     * If both this object returns true for the other, and the other returns true
     * for this one, the collisions may occur when they overlap, meaning that their
     * respective onCollisionEnter/onCollisionStay/onCollisionExit will be called.
     * Note that this assumes that both objects have been added to the same
     * GameObjectCollection, and that its handleCollisions() method is invoked.
     *
     * The user main paddle is the only object that a fallen heart can collide with.
     *
     * @param other The other GameObject.
     * @return true if the objects should collide. This does not guarantee a collision
     * would actually collide if they overlap, since the other object has to confirm
     * this one as well.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && Objects.equals(other.getTag(), this.mainPaddle.getTag());
    }
}
