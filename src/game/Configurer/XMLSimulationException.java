package game.Configurer;


/**
 * This class handles any errors that are have to do with the configuration XML file
 */
public class XMLSimulationException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on an issue in the code;
     */
    public XMLSimulationException (String message, Object ... values) {
        super(String.format(message, values));
    }

}
