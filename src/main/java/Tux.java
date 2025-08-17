import java.util.Scanner;

public class Tux {

    private static final String NAME = "Tux";
    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT = "bye";

    private static final Scanner scanner = new Scanner(System.in);

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

    private static void handleUserInput() {
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals(EXIT)) {
                break;
            } else {
                formatMessage(userInput);
            }
        }
    }

    public static void main(String[] args) {
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
