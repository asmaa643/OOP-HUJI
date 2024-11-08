/**
 * A renderer creator class.
 */
public class RendererFactory {
    private static final String CONSOLE_RENDERER = "console";
    private static final String VOID_RENDERER = "none";

    /**
     * Constructor.
     */
    public RendererFactory(){}

    /**
     * Creates a renderer object according to the type.
     * @param type of the renderer.
     * @param size of the board to renderer.
     * @return a renderer of the given type.
     */
    public Renderer buildRenderer(String type, int size){
        switch (type){
            case  CONSOLE_RENDERER:
                return new ConsoleRenderer(size);
            case VOID_RENDERER:
                return new VoidRenderer();
            default:
                return null;
        }
    }

}
