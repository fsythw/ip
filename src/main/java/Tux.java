import exceptions.TaskException;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class Tux {
//
//    public static final String NAME = "Tux";
//    public static final String DIVIDER = "____________________________________________________________";
//    public static final String EXIT = "bye";
//    public static final String FILE_NAME = "./data/tux.dat";
//
//
//    private static final Scanner scanner = new Scanner(System.in);
//
//    private static final ui.InputHandler ih = new ui.InputHandler();
//
//    private static String greetUser() {
//        return "Hello! I'm %s\nWhat can I do for you?".formatted(NAME);
//    }
//
//    private static String exit() {
//        return "Bye. Hope to see you again soon!";
//    }
//
//    private static void formatMessage(String message) {
//        System.out.println(DIVIDER);
//        System.out.println(message);
//        System.out.println(DIVIDER);
//    }
//
//    private static void handleUserInput() throws TaskException {
//        while (true) {
//            String userInput = scanner.nextLine().trim();
//
//            if (userInput.equals(EXIT)) {
//                break;
//            }
//            String result = ih.handleInput(userInput);
//
//            formatMessage(result);
//        }
//    }
//
//    public static void main(String[] args) throws TaskException {
//
//        List<tasks.Task> loadedTasks = ui.InputHandler.loadTasks(FILE_NAME);
//        ih.setTaskList(loadedTasks);
//
//        formatMessage(greetUser());
//        handleUserInput();
//        formatMessage(exit());
//
//    }
//}
import storage.Storage;
import tasks.TaskList;
import ui.InputHandler;
import ui.Ui;

public class Tux {

    private static final String FILE_NAME = "./data/tux.dat";

    public static void main(String[] args) throws TaskException {
        Ui ui = new Ui();
        Storage storage = new Storage(FILE_NAME);
        TaskList taskList = new TaskList(storage.load());
        InputHandler ih = new InputHandler(taskList, storage);

        ui.greetUser();

        while (true) {
            String userInput = ui.readCommand();
            if (userInput.equalsIgnoreCase("bye")) break;

            String response = ih.handleInput(userInput);
            ui.showMessage(response);
        }

        ui.exit();
    }
}

