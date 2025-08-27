package tux.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import tux.exceptions.TaskException;
import tux.tasks.Deadline;
import tux.tasks.Event;
import tux.tasks.Task;
import tux.tasks.TaskList;
import tux.tasks.ToDo;

/**
 * Handles persistent storage of tasks in a file.
 * This Storage class handles reading and writing tasks from/to a text file.
 * It encodes Task objects into a line-based format for writing
 * and decodes them back into Task objects when reading.
 */
public class Storage {

    private final Path filePath;

    /**
     * Creates a Storage instance with the given file path.
     * @param filePath The hardcoded path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads all tasks from file into memory.
     * Each line is decoded into a type of Task.
     * If the file does not exist, it is created along with the necessary parent directories.
     * @return TaskList containing all tasks read. If the file is empty, returns empty list.
     * @throws TaskException If the file cannot be read or contains corrupt data.
     */
    public List<Task> load() throws TaskException {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                return tasks;
            }
            try (BufferedReader br = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    Task t = decode(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                }
            }
        } catch (IOException e) {
            throw new TaskException("Could not load tux.tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the given TaskList of tasks to the file.
     * Each task is encoded into a single line. Overwrites the file contents.
     * @param taskList The list of tasks to be written.
     * @throws TaskException If the file cannot be created or written to.
     */
    public void save(TaskList taskList) throws TaskException {
        try {
            Files.createDirectories(filePath.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                for (Task t : taskList.asList()) {
                    bw.write(encode(t));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new TaskException("Could not save tasks: " + e.getMessage());
        }
    }

    /**
     * Encodes a single Task object into a String.
     * @param t The Task object to be encoded.
     * @return String object with the format type | id | description
     */
    private String encode(Task t) {
        String done = t.getDone() ? "1" : "0";
        if (t instanceof ToDo) {
            return String.join(" | ", "T", done, ((ToDo) t).getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, d.getDescription(), d.getBy().toString());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", done, e.getDescription(),
                    e.getFrom().toString(), e.getTo().toString());
        }
        // fallback — treat as tux.tasks.ToDo
        return String.join(" | ", "T", done, t.getDescription());
    }

    /**
     * Decodes a single line from the file into a Task object.
     * @param line A String object read from the file.
     * @return A subtype of a Task object.
     * @throws TaskException If the line is in incorrect format.
     */
    private Task decode(String line) throws TaskException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new TaskException("Corrupt line: " + line);
        }
        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        switch (type) {
        case "T": {
            String desc = parts[2];
            Task t = new ToDo(desc);
            if (done) {
                t.markDone();
            }
            return t;
        }
        case "D": {
            if (parts.length < 4) {
                throw new TaskException("Corrupt deadline: " + line);
            }
            String desc = parts[2];
            LocalDate by = LocalDate.parse(parts[3]);
            Task t = new Deadline(desc, by);
            if (done) {
                t.markDone();
            }
            return t;
        }
        case "E": {
            if (parts.length < 5) {
                throw new TaskException("Corrupt event: " + line);
            }
            String desc = parts[2];
            LocalDate from = LocalDate.parse(parts[3]);
            LocalDate to = LocalDate.parse(parts[4]);
            Task t = new Event(desc, from, to);
            if (done) {
                t.markDone();
            }
            return t;
        }
        default:
            throw new TaskException("Unknown type in save file: " + type);
        }
    }
}
