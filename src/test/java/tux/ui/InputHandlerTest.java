package tux.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tux.exceptions.TaskException;
import tux.storage.Storage;
import tux.tasks.TaskList;

/**
 * Used ChatGPT to generate invalid test cases.
 */
public class InputHandlerTest {

    private InputHandler ih;

    @BeforeEach
    void setUp() {
        TaskList taskList = new TaskList();
        Storage storage = new Storage("testData/tasks.txt");
        ih = new InputHandler(taskList, storage);
    }

    @Test
    void createDeadline_validInput_success() throws Exception {
        String result = ih.createDeadline("deadline finish report /by 2025-09-15");
        assertTrue(result.contains("finish report"), "Should contain task description");
        assertTrue(result.contains("Sep 15 2025"), "incorrect format for deadline task");
    }

    @Test
    void createDeadline_missingBy_throwsException() {
        TaskException ex = assertThrows(TaskException.class, () ->
                ih.createDeadline("deadline finish report"));
        assertEquals("error: Deadline task must contain /by!", ex.getMessage());
    }

    @Test
    void createDeadline_incorrectFormat_throwsException() {
        TaskException ex = assertThrows(TaskException.class, () ->
                ih.createDeadline("Finish report /by not-a-date"));
        assertTrue(ex.getMessage().toLowerCase().contains("incorrect"),
                "error: incorrect format for deadline task");
    }

}
