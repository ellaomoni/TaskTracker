import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TaskManager {
    private List<Task> tasks;
    private JsonFileHandler fileHandler;
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());

    public TaskManager() {
        this.fileHandler = new JsonFileHandler();
        try {
            this.tasks = fileHandler.loadTasks();
        } catch (IOException e) {
            this.tasks = new ArrayList<>();
        }
    }

    public void addTask(Task task) {
        int nextId = 1;
        for (Task t : tasks) {
            if (t.getId() >= nextId) nextId = t.getId() + 1;
        }
        task.setId(nextId);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        if (task.getStatus() == null || task.getStatus().isEmpty()) task.setStatus("TODO");
        tasks.add(task);
        try {
            JsonFileHandler.saveTasks(tasks);
        } catch (IOException e) {
            LOGGER.warning("Failed to save tasks after add: " + e.getMessage());
        }
    }

    public void deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            try {
                JsonFileHandler.saveTasks(tasks);
            } catch (IOException e) {
                LOGGER.warning("Failed to save tasks after delete: " + e.getMessage());
            }
        }
    }

    public void updateTask(Task task) {
        for (Task t : tasks) {
            if (t.getId() == task.getId()) {
                t.setDescription(task.getDescription());
                if (task.getStatus() != null && !task.getStatus().isEmpty()) {
                    t.setStatus(task.getStatus());
                }
                t.setUpdatedAt(LocalDateTime.now());
                try {
                    JsonFileHandler.saveTasks(tasks);
                } catch (IOException e) {
                    LOGGER.warning("Failed to save tasks after update: " + e.getMessage());
                }
                break;
            }
        }
    }

    public void markInProgress(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setStatus("IN_PROGRESS");
                t.setUpdatedAt(LocalDateTime.now());
                try {
                    JsonFileHandler.saveTasks(tasks);
                } catch (IOException e) {
                    LOGGER.warning("Failed to save tasks after markInProgress: " + e.getMessage());
                }
                break;
            }
        }
    }

    public void markDone(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setStatus("DONE");
                t.setUpdatedAt(LocalDateTime.now());
                try {
                    JsonFileHandler.saveTasks(tasks);
                } catch (IOException e) {
                    LOGGER.warning("Failed to save tasks after markDone: " + e.getMessage());
                }
                break;
            }
        }
    }

    public List<Task> listTasks() {
        return tasks;
    }
}
