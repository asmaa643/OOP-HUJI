package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents the adding pucks strategy.
 */
public class AdditionalBall implements CollisionStrategy{

    private final BrickerGameManager brickerGameManager;

    /**
     * Constructor of the strategy.
     * @param brickerGameManager the manger of the game.
     */
    public AdditionalBall(BrickerGameManager brickerGameManager){

        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Adds 2 pucks in the center.
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.removeBrick((Brick) thisObj);
        brickerGameManager.addAdditionalBallObject();
        brickerGameManager.addAdditionalBallObject();
    }
}
