package storage;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.TaskException;
import tasks.*;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

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
                    if (line.isEmpty()) continue;
                    Task t = decode(line);
                    if (t != null) tasks.add(t);
                }
            }
        } catch (IOException e) {
            throw new TaskException("Could not load tasks: " + e.getMessage());
        }
        return tasks;
    }

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

    // Format:
    // T | 1 | read book
    // D | 0 | return book | 2025-06-06
    // E | 1 | project meeting | 2025-08-06 | 2025-08-06
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
        // fallback — treat as tasks.ToDo
        return String.join(" | ", "T", done, t.getDescription());
    }

    private Task decode(String line) throws TaskException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) throw new TaskException("Corrupt line: " + line);
        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        switch (type) {
            case "T": {
                String desc = parts[2];
                Task t = new ToDo(desc);
                if (done) t.markDone();
                return t;
            }
            case "D": {
                if (parts.length < 4) throw new TaskException("Corrupt deadline: " + line);
                String desc = parts[2];
                LocalDate by = LocalDate.parse(parts[3]);
                Task t = new Deadline(desc, by);
                if (done) t.markDone();
                return t;
            }
            case "E": {
                if (parts.length < 5) throw new TaskException("Corrupt event: " + line);
                String desc = parts[2];
                LocalDate from = LocalDate.parse(parts[3]);
                LocalDate to = LocalDate.parse(parts[4]);
                Task t = new Event(desc, from, to);
                if (done) t.markDone();
                return t;
            }
            default:
                throw new TaskException("Unknown type in save file: " + type);
        }
    }
}
