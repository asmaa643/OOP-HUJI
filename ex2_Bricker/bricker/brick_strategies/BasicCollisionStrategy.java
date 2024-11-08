package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents the basic strategy.
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    private final BrickerGameManager brickerGameManager;

    /**
     * Constructor of the strategy.
     * @param brickerGameManager manager of the game.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager){

        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Only removes the brick.
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.removeBrick((Brick) thisObj);
    }
}
