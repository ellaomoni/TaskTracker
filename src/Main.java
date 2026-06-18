import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        TaskManager taskmanager = new TaskManager();
        String command = args[0].toLowerCase();

        //Handle commands
        switch (command) {
            case "list":
                List<Task> tasks = taskmanager.listTasks();
                LOGGER.info("Tasks: " + tasks.size());
                break;

            case "delete":
                if (args.length < 2) {
                    LOGGER.warning("Usage: delete <id>");
                    break;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    taskmanager.deleteTask(id);
                    LOGGER.info("Requested delete for id=" + id);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid id: " + args[1]);
                }
                break;

            case "inprogress":
                if (args.length < 2) {
                    LOGGER.warning("Usage: inprogress <id>");
                    break;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    taskmanager.markInProgress(id);
                    LOGGER.info("Requested markInProgress for id=" + id);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid id: " + args[1]);
                }
                break;

            case "done":
                if (args.length < 2) {
                    LOGGER.warning("Usage: done <id>");
                    break;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    taskmanager.markDone(id);
                    LOGGER.info("Requested markDone for id=" + id);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid id: " + args[1]);
                }
                break;

            case "add":
                if (args.length < 2) {
                    LOGGER.warning("Usage: add <description>");
                    break;
                }
                String desc = args[1];
                Task newTask = new Task(0, desc, "TODO", LocalDateTime.now(), LocalDateTime.now());
                taskmanager.addTask(newTask);
                LOGGER.info("Requested add: " + desc);
                break;

            case "update":
                if (args.length < 3) {
                    LOGGER.warning("Usage: update <id> <description>");
                    break;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    String newDesc = args[2];
                    Task updated = new Task(id, newDesc, "TODO", LocalDateTime.now(), LocalDateTime.now());
                    taskmanager.updateTask(updated);
                    LOGGER.info("Requested update for id=" + id);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid id: " + args[1]);
                }
                break;

            case "help":
            default:
                printHelp();
                break;
        }
    }

    private static void printHelp() {
        LOGGER.info("Usage: java Main <command> [args]");
        LOGGER.info("Commands:");
        LOGGER.info("  list\t\t\tList tasks");
        LOGGER.info("  add <desc>\t\tAdd a task");
        LOGGER.info("  update <id> <desc>\tUpdate a task");
        LOGGER.info("  delete <id>\t\tDelete a task");
        LOGGER.info("  inprogress <id>\tMark task in progress");
        LOGGER.info("  done <id>\t\tMark task done");
    }
}