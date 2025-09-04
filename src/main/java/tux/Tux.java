package tux;

import tux.exceptions.TaskException;
import tux.storage.Storage;
import tux.tasks.TaskList;
import tux.ui.InputHandler;

/**
 * Entry point for Tux.
 */
public class Tux {

    private static final String FILE_NAME = "./data/tux.dat";
    private final Storage storage;
    private final InputHandler ih;

    /**
     * Creates the Tux chatbot.
     */
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

}

