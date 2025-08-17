import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.StringBuilder;

public class Tux {

    private static final String NAME = "Tux";
    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT = "bye";
    private static final String TASKLIST = "list";

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> taskList = new ArrayList<>();
    private static final StringBuilder sb = new StringBuilder();

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

    private static void addToTaskList(String userInput) {
        Tux.taskList.add(userInput);
        formatMessage("added: %s".formatted(userInput));
    }

    private static void enumerateTaskList() {
        for (int i = 0; i < Tux.taskList.size(); i++) {
            //formatMessage("%s. %s".formatted(i+1, taskList.get(i)));
            Tux.sb.append("%s. %s\n".formatted(i+1, taskList.get(i)));
        }
        formatMessage(sb.toString());
    }


    private static void handleUserInput() {
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals(EXIT)) {
                break;
            } else if (userInput.equals(TASKLIST)){
                enumerateTaskList();
            } else {
                addToTaskList(userInput);

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
