package tux.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import tux.exceptions.TaskException;
import tux.storage.Storage;
import tux.tasks.Deadline;
import tux.tasks.Event;
import tux.tasks.Task;
import tux.tasks.TaskList;
import tux.tasks.ToDo;
import tux.types.Command;

/**
 * Parses and executes user commands to manipulate tasks.
 * Translates user input into actions on the TaskList, and updates the Storage object suitably.
 */
public class InputHandler {

    private static final String BY = "/by";
    private static final String FROM = "/from";
    private static final String TO = "/to";
    private static final StringBuilder sb = new StringBuilder();

    private final TaskList taskList;
    private final Storage storage;
    //private static final List<tux.tasks.Task> taskList = new ArrayList<tux.tasks.Task>();

    /**
     * Constructs an InputHandler.
     * @param taskList The list of tasks currently in memory.
     * @param storage The Storage object responsible for loading and saving data.
     */
    public InputHandler(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Parses user command string and performs the corresponding action.
     * @param userInput The raw command input (e.g. todo buy bread)
     * @return A response message after performing the action.
     */
    public String handleInput(String userInput) {
        Command command = getInstruction(userInput.trim().split(" ")[0]);
        String msg = userInput.substring(userInput.trim().split(" ")[0].length()).trim();

        try {
            return switch (command) {
            case MARK -> markDone(msg);
            case UNMARK -> markUndone(msg);
            case TODO -> createToDo(msg);
            case DEADLINE -> createDeadline(msg);
            case EVENT -> createEvent(msg);
            case LIST -> enumerateTaskList();
            case DELETE -> deleteTask(msg);
            case FIND -> findTask(msg);
            default -> "going to handle this";
            };
        } catch (TaskException e) {
            return e.getMessage();
        }
    }

    private Command getInstruction(String instruction) {
        return Command.valueOf(instruction.toUpperCase());
    }

    private String addToTaskList(Task task) throws TaskException {
        taskList.add(task);
        storage.save(taskList);
        return "Got it. I've added this task:\n"
                + task.getTaskDescription()
                + "\nNow you have %d tasks in the list.".formatted(taskList.size());
    }

    private String enumerateTaskList() {
        sb.setLength(0);
        for (int i = 0; i < taskList.size(); i++) {
            sb.append("%s.%s\n".formatted(i + 1, taskList.get(i).getTaskDescription()));
        }
        return sb.toString();
    }

    /**
     * Marks a task as completed.
     *
     * @param index The index of the task in TaskList.
     * @return String confirmation message.
     * @throws TaskException If the index is invalid or storage cannot be updated.
     */
    private String markDone(String index) throws TaskException {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex - 1);
        currentTask.markDone();
        storage.save(taskList);
        return "Nice! I've marked this task as done:\n%s".formatted(currentTask.getTaskDescription());
    }

    /**
     * Marks a task as not completed.
     *
     * @param index The index of the task in the TaskList.
     * @return String confirmation message.
     * @throws TaskException If the index is invalid or storage cannot be updated.
     */
    private String markUndone(String index) throws TaskException {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex - 1);
        currentTask.markUndone();
        storage.save(taskList);
        return "Ok, I've marked this task as not done yet:\n%s".formatted(currentTask.getTaskDescription());
    }

    /**
     * Creates a ToDo task from user input and adds it to TaskList.
     *
     * @param userInput String description of the task.
     * @return String confirmation message after adding.
     * @throws TaskException If the description is blank.
     */
    public String createToDo(String userInput) throws TaskException {
        if (userInput.isBlank()) {
            throw new TaskException("Description is empty!");
        }

        Task newToDo = new ToDo(userInput);
        return addToTaskList(newToDo);
    }

    /**
     * Creates a Deadline task from user input and adds it to TaskList.
     *
     * @param userInput String input containing description and "/by" date.
     * @return String confirmation message after adding.
     * @throws TaskException If format is invalid or date cannot be parsed into LocalDate object.
     */
    public String createDeadline(String userInput) throws TaskException {
        if (!userInput.contains(BY)) {
            throw new TaskException("Deadline task must contain /by!");
        }

        String[] handledUserInput = userInput.split(BY);
        if (handledUserInput.length != 2) {
            throw new TaskException("Incorrect deadline format");
        }
        String description = userInput.split(BY)[0].trim();
        String byStr = userInput.split(BY)[1].trim();

        LocalDate by;
        try {
            by = LocalDate.parse(byStr);
        } catch (DateTimeParseException e) {
            throw new TaskException("incorrect format for deadline task");
        }
        Task newDeadline = new Deadline(description, by);
        return addToTaskList(newDeadline);
    }

    /**
     * Creates an Event task from user input and adds it to TaskList.
     *
     * @param userInput String input containing description, "/from" and "/to" dates.
     * @return String confirmation message after adding.
     * @throws TaskException If format is invalid or date cannot be parsed into LocalDate object.
     */
    public String createEvent(String userInput) throws TaskException {
        int fromIndex = userInput.indexOf(FROM);
        int toIndex = userInput.indexOf(TO);

        if (fromIndex == -1 || toIndex == -1) {
            throw new TaskException("Event task must contain /from and /to!");
        }

        String description = userInput.substring(0, fromIndex).trim();
        String fromStr = userInput.substring(fromIndex + FROM.length(), toIndex).trim();
        String toStr = userInput.substring(toIndex + TO.length()).trim();

        LocalDate from;
        LocalDate to;
        try {
            from = LocalDate.parse(fromStr);
            to = LocalDate.parse(toStr);
        } catch (DateTimeParseException e) {
            throw new TaskException("incorrect format for deadline task");
        }
        Task newEvent = new Event(description, from, to);
        return addToTaskList(newEvent);
    }

    /**
     * Deletes a task by index.
     *
     * @param index The index of the task in the TaskList.
     * @return String confirmation message after deletion.
     * @throws TaskException If the index is invalid or storage cannot be updated.
     */
    public String deleteTask(String index) throws TaskException {
        int taskIndex = Integer.parseInt(index);
        Task removedTask = taskList.delete(taskIndex - 1);
        storage.save(taskList);
        return "Noted I've removed this task: \n%s".formatted(removedTask.getTaskDescription())
                + "\nNow you have %d tux.tasks in the list.".formatted(taskList.size());
    }

    /**
     * Searches for tasks (includes partial searching) using the given keyword.
     * @param keyword String to search for
     * @return String containing parsed tasks
     * @throws TaskException
     */
    public String findTask(String keyword) throws TaskException {
        if (keyword == null || keyword.isBlank()) {
            throw new TaskException("Please provide a keyword to search");
        }

        String lowerKeyword = keyword.toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");

        int matchCount = 0;
        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            String currentTaskDescription = currentTask.getTaskDescription();
            if (currentTaskDescription.toLowerCase().contains(lowerKeyword)) {
                matchCount++;
                sb.append("%d.%s\n".formatted(matchCount, currentTaskDescription));
            }
        }

        if (matchCount == 0) {
            return "No matching tasks found for \"" + keyword + "\".";
        }

        return sb.toString().trim();

    }


}
