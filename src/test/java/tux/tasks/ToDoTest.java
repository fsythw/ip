package tux.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class ToDoTest {
    @Test
    public void createToDo_validInput_success() {
        ToDo temporaryToDo = new ToDo("buy bread");
        assertEquals("[T][ ] buy bread", temporaryToDo.getTaskDescription());
    }
}
