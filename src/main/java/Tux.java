import exceptions.TaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.StringBuilder;

public class Tux {

    private static final String NAME = "Tux";
    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT = "bye";
    private static final String TASKLIST = "list";
    private static final String BY = "/by";
    private static final String FROM = "/from";
    private static final String TO = "/to";

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Task> taskList = new ArrayList<Task>();
    private static final StringBuilder sb = new StringBuilder();

    private static String greetUser() {
        return "Hello! I'm %s\nWhat can I do for you?".formatted(NAME);
    }

    private static String exit() {
        return "Bye. Hope to see you again soon!";
    }

    private static void formatMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    private static void addToTaskList(Task task) {
        Tux.taskList.add(task);
        formatMessage("Got it. I've added this task:\n"
                + task.getTaskDescription()
                + "\nNow you have %d tasks in the list.".formatted(Tux.taskList.size()));
    }

    private static void enumerateTaskList() {
        Tux.sb.setLength(0);
        for (int i = 0; i < Tux.taskList.size(); i++) {
            Tux.sb.append("%s.%s\n".formatted(i+1, taskList.get(i).getTaskDescription()));
        }
        formatMessage(sb.toString());
    }

    private static void markDone(String index) {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = Tux.taskList.get(taskIndex-1);
        currentTask.markDone();
        formatMessage("Nice! I've marked this task as done:\n%s".formatted(currentTask.getTaskDescription()));
    }

    private static void markUndone(String index) {
        int taskIndex = Integer.parseInt(index);
        Task currentTask = Tux.taskList.get(taskIndex-1);
        currentTask.markUndone();
        formatMessage("Ok, I've marked this task as not done yet:\n%s".formatted(currentTask.getTaskDescription()));
    }

    public static void createToDo(String userInput) throws TaskException {
        if (userInput.isBlank()) {
            throw new TaskException("Description is empty!");
        }

        Task newToDo = new ToDo(userInput);
        addToTaskList(newToDo);
    }

    public static void createDeadline(String userInput) throws TaskException {
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
        addToTaskList(newDeadline);
    }

    public static void createEvent(String userInput) throws TaskException {
        int fromIndex = userInput.indexOf(FROM);
        int toIndex = userInput.indexOf(TO);

        if (fromIndex == -1 || toIndex == -1) {
            throw new TaskException("Event task must contain /from and /to!");
        }

        String description = userInput.substring(0, fromIndex).trim();
        String from = userInput.substring(fromIndex + FROM.length(), toIndex).trim();
        String to = userInput.substring(toIndex + TO.length()).trim();

        Task newEvent = new Event(description, from, to);
        addToTaskList(newEvent);
    }



    private static void handleUserInput() throws TaskException {
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals(EXIT)) {
                break;
            } else if (userInput.equals(TASKLIST)) {
                enumerateTaskList();
            } else if (userInput.substring(0,4).equals("mark")) {
                markDone(userInput.substring(5));
            } else if (userInput.substring(0,6).equals("unmark")){
                markUndone(userInput.substring(7));
            } else if (userInput.substring(0,4).equals("todo")) {
                createToDo(userInput.substring(5));
            } else if (userInput.substring(0,8).equals("deadline")) {
                createDeadline(userInput.substring(9));
            } else if (userInput.substring(0,5).equals("event")) {
                createEvent(userInput.substring(6));
            } else {
                continue;
            }
        }
    }

    public static void main(String[] args) throws TaskException {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        String name = "Tux";
//        System.out.println("Hello from " + name);
        formatMessage(greetUser());
        handleUserInput();
        formatMessage(exit());

    }
}
