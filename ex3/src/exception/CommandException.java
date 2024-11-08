package exception;

/**
 * Represents an UserCommandException Throwable.
 */
public class CommandException extends Throwable{
    /**
     * Constructs a new throwable with a detail message.
     */
    public CommandException(String msg) {
        super(msg);
    }
}
