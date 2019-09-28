package game.Simulation;

public class StateIncompatibleException extends IndexOutOfBoundsException {
    private static final long serialVersionUID = 2L;

    /**
     * Create an exception based on an issue in the code;
     */
    public StateIncompatibleException (String message, Object ... values) {
        super(String.format(message, values));
    }
}
