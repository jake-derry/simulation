package game.Configurer.ExceptionHandlers;

import javafx.scene.control.Alert;

/**
 * When called, the ErrorThrow class will create an alert that will be displayed on the dialog box. This class will not
 * throw an exception. Once returned, the caller's method will continue to run to completion.
 *
 */
public class ErrorThrow{

    /** Creates an Alert. Use when a string has variables to incorporate into the message.
     *
     * @param message Message of the error
     * @param values
     */
    public ErrorThrow ( String message, Object ... values) {
        new Alert(Alert.AlertType.ERROR, String.format(message, values)).showAndWait();
    }

    /** Creates an Alert based on message.
     *
     * @param message Message of the error
     */
    public ErrorThrow( String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}
