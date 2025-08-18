package exceptions;

public class TaskException extends Exception {
    public TaskException(String msg) {
        super("error: " + msg);
    }
}