package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents the falling heart strategy.
 */
public class ReturnHearts implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs the fallen heart strategy.
     */
    public ReturnHearts(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Creates a fallen heart from the brick.
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.removeBrick((Brick) thisObj);
        brickerGameManager.addHeart(thisObj);
    }
}
