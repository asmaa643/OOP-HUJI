package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Brick Collision Strategy interface.
 */
public interface CollisionStrategy {
    /**
     * What to do when ball collides with a brick, and removes the brick..
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    public void onCollision(GameObject thisObj, GameObject otherObj);
}
