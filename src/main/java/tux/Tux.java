package tux;

import tux.exceptions.TaskException;

import tux.storage.Storage;
import tux.tasks.TaskList;
import tux.ui.InputHandler;
import tux.ui.Ui;

/**
 * Entry point for Tux.
 */
public class Tux {

    private static final String FILE_NAME = "./data/tux.dat";
    private final Storage storage;
    private final InputHandler ih;

    public Tux() {
        storage = new Storage(FILE_NAME);
        TaskList taskList;

        try {
            taskList = new TaskList(storage.load());
        } catch (TaskException e) {
            taskList = new TaskList();
        }

        ih = new InputHandler(taskList, storage);

    }

    public String getResponse(String userInput) {

        String response = ih.handleInput(userInput);
        return response;
    }
    /**
     * Main method to run Tux.
     * This creates an instance of Tux with the Ui object and reads user inpu
     * @param args Command line arguments.
     * @throws TaskException
     */
//    public static void main(String[] args) throws TaskException {
//        Ui ui = new Ui();
//        Storage storage = new Storage(FILE_NAME);
//        TaskList taskList = new TaskList(storage.load());
//        InputHandler ih = new InputHandler(taskList, storage);
//
//        ui.greetUser();
//
//        while (true) {
//            String userInput = ui.readCommand();
//            if (userInput.equalsIgnoreCase("bye")) {
//                break;
//            }
//
//            String response = ih.handleInput(userInput);
//            ui.showMessage(response);
//        }
//
//        ui.exit();
//    }
}

