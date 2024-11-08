package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Represents the double strategy, it holds another two strategies.
 */
public class DoubleBehavior implements CollisionStrategy{
    private final CollisionStrategy collisionStrategy1;
    private final CollisionStrategy collisionStrategy2;

    /**
     * The strategy constructor.
     * @param collisionStrategy1 1st strategy to do when collision.
     * @param collisionStrategy2 2nd strategy to do when collision.
     */
    public DoubleBehavior( CollisionStrategy collisionStrategy1, CollisionStrategy collisionStrategy2) {
        this.collisionStrategy1 = collisionStrategy1;
        this.collisionStrategy2 = collisionStrategy2;

    }

    /**
     * Calls the two strategies.
     * @param thisObj the brick.
     * @param otherObj the ball (main one or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        if (collisionStrategy1 != null){
            collisionStrategy1.onCollision(thisObj, otherObj);
        }
        if (collisionStrategy2 != null){
            collisionStrategy2.onCollision(thisObj, otherObj);
        }
    }
}
