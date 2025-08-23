import exceptions.TaskException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import types.Command;

public class InputHandler {

    private static final String BY = "/by";
    private static final String FROM = "/from";
    private static final String TO = "/to";

    private static final List<Task> taskList = new ArrayList<Task>();
    private static final StringBuilder sb = new StringBuilder();

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
        taskList.clear();
        taskList.addAll(tasks);
    }

    public static List<Task> loadTasks(String filepath) {
        List<Task> tasks = new ArrayList<>();
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(filepath);
            if (!java.nio.file.Files.exists(path)) {
                java.nio.file.Files.createDirectories(path.getParent());
                java.nio.file.Files.createFile(path);
                return tasks;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    for (Object o : (List<?>) obj) {
                        if (o instanceof Task) {
                            tasks.add((Task) o);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load tasks: " + e.getMessage());
        }

        return tasks;
    }

    private void saveTasks() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(Tux.FILE_NAME);
            if (!java.nio.file.Files.exists(path.getParent())) {
                java.nio.file.Files.createDirectories(path.getParent());
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Tux.FILE_NAME))) {
                oos.writeObject(new ArrayList<>(taskList));
            }
        } catch (IOException e) {
            System.out.println("Could not save tasks: " + e.getMessage());
        }
    }

    private String addToTaskList(Task task) {
        taskList.add(task);
        saveTasks();
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
        saveTasks();
        return "Nice! I've marked this task as done:\n%s".formatted(currentTask.getTaskDescription());
    }

    private String markUndone(String index) {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = taskList.get(taskIndex-1);
        currentTask.markUndone();
        saveTasks();
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
        saveTasks();
        return "Noted I've removed this task: \n%s".formatted(removedTask.getTaskDescription()) + "\nNow you have %d tasks in the list.".formatted(taskList.size());
    }


}
