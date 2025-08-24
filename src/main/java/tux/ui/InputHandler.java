package tux.ui;

import tux.exceptions.TaskException;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import tux.storage.Storage;
import tux.tasks.*;
import tux.types.*;

public class InputHandler {

    private static final String BY = "/by";
    private static final String FROM = "/from";
    private static final String TO = "/to";

    private final TaskList taskList;
    private final Storage storage;
    //private static final List<tux.tasks.Task> taskList = new ArrayList<tux.tasks.Task>();
    private static final StringBuilder sb = new StringBuilder();

    public InputHandler(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }
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
                default -> "going to handle this";
            };
        } catch (TaskException e) {
            return e.getMessage();
        }
    }

    private Command getInstruction(String instruction) {
        return Command.valueOf(instruction.toUpperCase());
    }

    public void setTaskList(List<Task> tasks) {
        this.taskList.clear();
        this.taskList.addAll(tasks);
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
            sb.append("%s.%s\n".formatted(i+1, taskList.get(i).getTaskDescription()));
        }
        return sb.toString();
    }

    private String markDone(String index) throws TaskException {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex-1);
        currentTask.markDone();
        storage.save(taskList);
        return "Nice! I've marked this task as done:\n%s".formatted(currentTask.getTaskDescription());
    }

    private String markUndone(String index) throws TaskException {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex-1);
        currentTask.markUndone();
        storage.save(taskList);
        return "Ok, I've marked this task as not done yet:\n%s".formatted(currentTask.getTaskDescription());
    }

    public String createToDo(String userInput) throws TaskException {
        if (userInput.isBlank()) {
            throw new TaskException("Description is empty!");
        }

        Task newToDo = new ToDo(userInput);
        return addToTaskList(newToDo);
    }

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

    public String createEvent(String userInput) throws TaskException {
        int fromIndex = userInput.indexOf(FROM);
        int toIndex = userInput.indexOf(TO);

        if (fromIndex == -1 || toIndex == -1) {
            throw new TaskException("Event task must contain /from and /to!");
        }

        String description = userInput.substring(0, fromIndex).trim();
        String fromStr = userInput.substring(fromIndex + FROM.length(), toIndex).trim();
        String toStr = userInput.substring(toIndex + TO.length()).trim();

        LocalDate from, to;
        try {
            from = LocalDate.parse(fromStr);
            to = LocalDate.parse(toStr);
        } catch (DateTimeParseException e) {
            throw new TaskException("incorrect format for deadline task");
        }
        Task newEvent = new Event(description, from, to);
        return addToTaskList(newEvent);
    }

    public String deleteTask(String index) throws TaskException {
        int taskIndex = Integer.parseInt(index);
        Task removedTask = taskList.delete(taskIndex);
        storage.save(taskList);
        return "Noted I've removed this task: \n%s".formatted(removedTask.getTaskDescription()) + "\nNow you have %d tux.tasks in the list.".formatted(taskList.size());
    }


}
