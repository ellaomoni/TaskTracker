import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class JsonFileHandler {
    private static final String FILE_PATH = "tasks.json";

    // Method to save a list of tasks to a JSON file
    public static void saveTasks(List<Task> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            sb.append("  {\n");
            sb.append("    \"id\": ").append(t.getId()).append(",\n");
            sb.append("    \"description\": \"").append(t.getDescription()).append("\",\n");
            sb.append("    \"status\": \"").append(t.getStatus()).append("\",\n");
            sb.append("    \"createdAt\": \"").append(t.getCreatedAt()).append("\",\n");
            sb.append("    \"updatedAt\": \"").append(t.getUpdatedAt()).append("\"\n");
            sb.append("  }");

            // Add a comma after every object except the last one
            if (i < tasks.size() - 1) {
                sb.append(",");
            }

            sb.append("\n");
        }

        sb.append("]");

        // Write the finished string to disk, overwriting the file each time
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(sb.toString());
        }
    }


    // Method to read tasks from a JSON file and return them as a string
    public static String readFile() throws IOException {
    StringBuilder sb = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }

    return sb.toString();
}

// Method to parse the JSON string and return a list of Task objects
public static List<String> parseBlocks(String json) {
    List<String> blocks = new ArrayList<>();

    if (json == null) return blocks;

    String trimmed = json.trim();
    if (trimmed.isEmpty()) return blocks;

    int startIdx = trimmed.indexOf('[');
    int endIdx = trimmed.lastIndexOf(']');
    if (startIdx == -1 || endIdx == -1 || endIdx <= startIdx) return blocks;

    String inner = trimmed.substring(startIdx + 1, endIdx).trim();
    if (inner.isEmpty()) return blocks;

    int i = 0;
    while (i < inner.length()) {
        int blockStart = inner.indexOf('{', i);
        if (blockStart == -1) break;
        int blockEnd = inner.indexOf('}', blockStart);
        if (blockEnd == -1) break;

        blocks.add(inner.substring(blockStart, blockEnd + 1).trim());
        i = blockEnd + 1;

        // skip commas and whitespace between objects
        while (i < inner.length() && (inner.charAt(i) == ',' || Character.isWhitespace(inner.charAt(i)))) i++;
    }

    return blocks;
}

// Method to extract a value for a given key from a JSON block
private static String extractValue(String block, String key) {
    // Find where the key appears, e.g.  "description":
    String searchFor = "\"" + key + "\":";
    int keyIndex = block.indexOf(searchFor);

    if (keyIndex == -1) return "";

    // Move past the key and colon to where the value starts
    int valueStart = keyIndex + searchFor.length();

    // Skip any whitespace or spaces after the colon
    while (valueStart < block.length() && block.charAt(valueStart) == ' ') {
        valueStart++;
    }

    // Is the value a quoted string, or a bare number?
    if (block.charAt(valueStart) == '"') {
        // String value — find the closing quote
        int valueEnd = block.indexOf('"', valueStart + 1);
        return block.substring(valueStart + 1, valueEnd);
    } else {
        // Numeric value — read until comma or newline
        int valueEnd = valueStart;
        while (valueEnd < block.length()
               && block.charAt(valueEnd) != ','
               && block.charAt(valueEnd) != '\n'
               && block.charAt(valueEnd) != '}') {
            valueEnd++;
        }
        return block.substring(valueStart, valueEnd).trim();
    }
}

// Method to parse a JSON block into a Task object
public static Task parseTaskBlock(String block) {
    int    id          = Integer.parseInt(extractValue(block, "id"));
    String description = extractValue(block, "description");
    String status      = extractValue(block, "status");
    LocalDateTime createdAt = LocalDateTime.parse(extractValue(block, "createdAt"));
    LocalDateTime updatedAt = LocalDateTime.parse(extractValue(block, "updatedAt"));

    return new Task(id, description, status, createdAt, updatedAt);
}

// Method to load tasks from the JSON file and return them as a list of Task objects
public static List<Task> loadTasks() throws IOException {
    List<Task> tasks = new ArrayList<>();

    // If the file doesn't exist yet, return an empty list
    java.io.File file = new java.io.File(FILE_PATH);
    if (!file.exists()) return tasks;

    String json = readFile();
    if (json.trim().isEmpty()) return tasks;
    List<String> blocks = parseBlocks(json);

    for (String block : blocks) {
        tasks.add(parseTaskBlock(block));
    }

    return tasks;
}
}
