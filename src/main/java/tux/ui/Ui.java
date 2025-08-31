package tux.ui;

import java.util.Scanner;

/**
 * Handles interactions with user.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NAME = "Tux";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays divider.
     */
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
     * Formats and displays a message for the user.
     * @param msg String message to be displayed.
     */
    public void showMessage(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    /**
     * Reads user input for processing.
     * @return Formatted String for TaskList manipulation.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }
}
