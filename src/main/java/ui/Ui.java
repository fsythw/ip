package ui;

import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NAME = "Tux";
    private final Scanner scanner = new Scanner(System.in);

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public void greetUser() {
        showLine();
        System.out.println("Hello! I'm " + NAME + "\nWhat can I do for you?");
        showLine();
    }

    public void exit() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showMessage(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showError(String msg) { System.out.println(msg); }

    public void showLoadingError() {
        System.out.println("Loading error. Starting with an empty task list.");
    }

    public String readCommand() { return scanner.nextLine().trim(); }
}
