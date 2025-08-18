import exceptions.TaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.StringBuilder;

public class Tux {

    private static final String NAME = "Tux";
    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT = "bye";


    private static final Scanner scanner = new Scanner(System.in);

    private static final InputHandler ih = new InputHandler();

    private static String greetUser() {
        return "Hello! I'm %s\nWhat can I do for you?".formatted(NAME);
    }

    private static String exit() {
        return "Bye. Hope to see you again soon!";
    }

    private static void formatMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(message);
        System.out.println(DIVIDER);
    }




    private static void handleUserInput() throws TaskException {
        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equals(EXIT)) {
                break;
            }
//
            String result = ih.handleInput(userInput);
            formatMessage(result);
        }
    }

    public static void main(String[] args) throws TaskException {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        String name = "Tux";
//        System.out.println("Hello from " + name);
        formatMessage(greetUser());
        handleUserInput();
        formatMessage(exit());

    }
}
