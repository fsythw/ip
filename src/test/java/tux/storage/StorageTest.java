package tux.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tux.exceptions.TaskException;
import tux.tasks.Deadline;
import tux.tasks.Task;
import tux.tasks.TaskList;
import tux.tasks.ToDo;


public class StorageTest {
    private Path tempFile;
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("tasks", ".txt");
        storage = new Storage(tempFile.toString());
        taskList = new TaskList();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void save_tasksArePersisted() throws Exception {
        Task todo = new ToDo("Buy groceries");
        Task deadline = new Deadline("Submit report", LocalDate.of(2025, 9, 15));
        taskList.add(todo);
        taskList.add(deadline);

        storage.save(taskList);

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(2, lines.size(), "Two tasks should be saved");
        assertTrue(lines.get(0).contains("Buy groceries"), "First line should contain ToDo task");
        assertTrue(lines.get(1).contains("Submit report"), "Second line should contain Deadline task");
    }

    @Test
    void save_ioErrorThrowsTaskException() throws Exception {
        // directory
        Storage badStorage = new Storage(tempFile.getParent().toString());
        TaskException ex = assertThrows(TaskException.class, () -> {
            badStorage.save(taskList);
        });
        assertTrue(ex.getMessage().startsWith("error: Could not save tasks"), ex.getMessage());

    }

}
