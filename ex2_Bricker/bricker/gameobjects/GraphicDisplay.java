package bricker.gameobjects;

import bricker.main.Constants;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;


/**
 * Displays the live result in a game.
 */
public class GraphicDisplay {

    private final ArrayList<Heart> hearts;
    private final GameObjectCollection gameObjects;
    private final Counter tries;


    /**
     * Graphic Display constructor:
     * Constructs the numeric display.
     * Constructs the hearts to display.
     *
     * @param heartImage     the renderable image of the heart object.
     * @param gameObjects    of the manager.
     * @param userPaddle     the game's main user paddle.
     * @param textRenderable the renderable image of the number.
     * @param triesCounter   counter of the tries.
     */
    public GraphicDisplay(ImageRenderable heartImage,
                          GameObjectCollection gameObjects,
                          Paddle userPaddle, TextRenderable textRenderable,
                          Counter triesCounter) {
        this.gameObjects = gameObjects;
        NumericDisplay numericDisplay = new NumericDisplay(new Vector2(Constants.DISPLAY_SIZE,
                Constants.BOARD_HEIGHT - Constants.DISPLAY_SIZE),
                new Vector2(Constants.DISPLAY_SIZE, Constants.DISPLAY_SIZE),
                textRenderable, triesCounter);
        this.gameObjects.addGameObject(numericDisplay, Layer.UI);
        numericDisplay.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        this.tries = triesCounter;
        this.hearts = new ArrayList<Heart>();
        for (int i = 0; i < Constants.MAX_TRIES; i++) {
            Heart heart = new Heart(new Vector2(2 * Constants.DISPLAY_SIZE + (i * Constants.SPACE),
                    Constants.BOARD_HEIGHT - Constants.DISPLAY_SIZE),
                    new Vector2(Constants.DISPLAY_SIZE, Constants.DISPLAY_SIZE),
                    heartImage, this, userPaddle);
            this.hearts.add(heart);
            gameObjects.addGameObject(heart, Layer.UI);
            heart.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        }
    }

    /**
     * A method that adds the heart that was collected by the main paddle.
     *
     * @param heart to add.
     */
    public void collectHeart(Heart heart) {
        if (this.hearts.size() < Constants.MAX_HEART_NUM) {
            this.tries.increment();
            this.hearts.add(heart);
            gameObjects.removeGameObject(heart);
            heart.setTopLeftCorner(new Vector2(2 * Constants.DISPLAY_SIZE +
                    ((this.hearts.size() - 1) * Constants.SPACE),
                    Constants.BOARD_HEIGHT - Constants.DISPLAY_SIZE));
            gameObjects.addGameObject(heart, Layer.UI);
            heart.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        } else {
            gameObjects.removeGameObject(heart);
        }
    }

    /**
     * Removes a single heart from the display when the ball falls.
     */
    public void removeHeart() {
        Heart heart = this.hearts.get(this.hearts.size() - 1);
        this.hearts.remove(this.hearts.size() - 1);
        gameObjects.removeGameObject(heart, Layer.UI);
        this.tries.decrement();
    }

    /**
     * Remaining tries getter
     *
     * @return tries number.
     */
    public int getTries() {
        return this.tries.value();
    }
}
