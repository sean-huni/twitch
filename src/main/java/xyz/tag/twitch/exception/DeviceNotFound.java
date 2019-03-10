package xyz.tag.twitch.exception;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.exception
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 15:40
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public class DeviceNotFound extends Exception{
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DeviceNotFound(String message) {
        super(message);
    }
}
