package tux.ui;

import java.util.Scanner;

/**
 * Handles interaction with user.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NAME = "tux.Tux";
    private final Scanner scanner = new Scanner(System.in);

    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays a greeting message for the user.
     */
    public void greetUser() {
        showLine();
        System.out.println("Hello! I'm " + NAME + "\nWhat can I do for you?");
        showLine();
    }

    /**
     * Displays an exit message for the user.
     */
    public void exit() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Takes a String message and adds dividers before and after it.
     * @param msg
     */
    public void showMessage(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showError(String msg) {
        System.out.println(msg);
    }

    public void showLoadingError() {
        System.out.println("Loading error. Starting with an empty task list.");
    }

    public String readCommand() {
        return scanner.nextLine().trim(); }
}
