package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

/**
 * Displays the number of the remaining tries.
 */
public class NumericDisplay extends GameObject {
    private static final int TWO_TRIES = 2;
    private final Counter tries;
    private final TextRenderable textRenderable;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param textRenderable    The textRenderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param tries of the player.
     */
    public NumericDisplay(Vector2 topLeftCorner, Vector2 dimensions,
                          TextRenderable textRenderable, Counter tries) {
        super(topLeftCorner, dimensions, textRenderable);
        this.textRenderable = textRenderable;
        this.tries = tries;
    }

    /**
     * Should be called once per frame, updates the number and the color of the remaining tries on the screen.
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
        this.textRenderable.setString(Integer.toString(this.tries.value()));
        if (this.tries.value() >= Constants.MAX_TRIES){
            this.textRenderable.setColor(Color.GREEN);
        } else if (this.tries.value() == TWO_TRIES) {
            this.textRenderable.setColor(Color.YELLOW);
        } else {
            this.textRenderable.setColor(Color.RED);
        }
    }

}
