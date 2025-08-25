package tux.exceptions;

/**
 * TaskException is a generic exception class that extends from Exception.
 */
public class TaskException extends Exception {
    public TaskException(String msg) {
        super("error: " + msg);
    }
}
