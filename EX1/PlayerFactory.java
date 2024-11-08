/**
 * A player creator class.
 */
public class PlayerFactory {
    private static final String HUMAN_PLAYER = "human";
    private static final String CLEVER_PLAYER = "clever";
    private static final String GENIUS_PLAYER = "genius";
    private static final String WHATEVER_PLAYER = "whatever";

    /**
     * Constructor.
     */
    public PlayerFactory(){}

    /**
     * Creates a player object according to the given type.
     * @param type of the player.
     * @return a player of the given type.
     */
    public Player buildPlayer(String type){
        switch (type.toLowerCase()){
            case HUMAN_PLAYER:
                return new HumanPlayer();
            case CLEVER_PLAYER:
                return new CleverPlayer();
            case WHATEVER_PLAYER:
                return new WhateverPlayer();
            case GENIUS_PLAYER:
                return new GeniusPlayer();
            default:
                return null;
        }
    }
}
