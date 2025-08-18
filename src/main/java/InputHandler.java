import exceptions.TaskException;

import java.util.ArrayList;
import java.util.List;
import types.Instruction;

public class InputHandler {

    private static final String BY = "/by";
    private static final String FROM = "/from";
    private static final String TO = "/to";

    private static final List<Task> taskList = new ArrayList<Task>();
    private static final StringBuilder sb = new StringBuilder();

    public String handleInput(String userInput) {
        Instruction instruction = getInstruction(userInput.trim().split(" ")[0]);
        String msg = userInput.substring(userInput.trim().split(" ")[0].length()).trim();

        try {
            return switch (instruction) {
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

    private Instruction getInstruction(String instruction) {
        return Instruction.valueOf(instruction.toUpperCase());
    }

    private String addToTaskList(Task task) {
        taskList.add(task);
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

    private String markDone(String index) {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex-1);
        currentTask.markDone();
        return "Nice! I've marked this task as done:\n%s".formatted(currentTask.getTaskDescription());
    }

    private String markUndone(String index) {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex-1);
        currentTask.markUndone();
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
        String by = userInput.split(BY)[1].trim();

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
        String from = userInput.substring(fromIndex + FROM.length(), toIndex).trim();
        String to = userInput.substring(toIndex + TO.length()).trim();

        Task newEvent = new Event(description, from, to);
        return addToTaskList(newEvent);
    }

    public String deleteTask(String index) {
        int taskIndex = Integer.parseInt(index);
        Task removedTask = taskList.remove(taskIndex-1);
        return "Noted I've removed this task: \n%s".formatted(removedTask.getTaskDescription()) + "\nNow you have %d tasks in the list.".formatted(taskList.size());
    }


}
