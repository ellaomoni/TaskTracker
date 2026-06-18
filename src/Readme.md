# Task Tracker CLI

A simple command-line app to add, manage, and track your tasks. All tasks are saved automatically to a `tasks.json` file in the same directory you run the program from.

---

## Requirements

- Java 11 or higher

---

## Setup

### 1. Clone or download the project

```bash
git clone https://github.com/ellaomoni/Software-Project.git
cd task-tracker
```

### 2. Compile the Java files

```bash
javac *.java
```

### 3. Run the app

```bash
java Main <command> [arguments]
```

---

## Commands

### Add a task

Creates a new task with a status of `TODO`.

```bash
java Main add "Your task description"
```

**Example:**
```bash
java Main add "Buy groceries"
```

---

### List all tasks

Displays all tasks currently saved.

```bash
java Main list
```

---

### Update a task

Updates the description of an existing task by its ID.

```bash
java Main update <id> "New description"
```

**Example:**
```bash
java Main update 1 "Buy groceries and cook dinner"
```

---

### Delete a task

Permanently removes a task by its ID.

```bash
java Main delete <id>
```

**Example:**
```bash
java Main delete 2
```

---

### Mark a task as In Progress

Changes the status of a task to `IN_PROGRESS`.

```bash
java Main inprogress <id>
```

**Example:**
```bash
java Main inprogress 1
```

---

### Mark a task as Done

Changes the status of a task to `DONE`.

```bash
java Main done <id>
```

**Example:**
```bash
java Main done 1
```

---

### Help

Displays a summary of all available commands.

```bash
java Main help
```

---

## Task Statuses

| Status        | Meaning                          |
|---------------|----------------------------------|
| `TODO`        | Task has been added but not started |
| `IN_PROGRESS` | Task is currently being worked on   |
| `DONE`        | Task has been completed             |

---

## How Data is Stored

All tasks are saved in a file called `tasks.json` in your current directory. This file is created automatically when you add your first task. You do not need to create it manually.

Each task is stored with the following fields:

```json
[
  {
    "id": 1,
    "description": "Buy groceries",
    "status": "TODO",
    "createdAt": "2025-01-15T10:30:00",
    "updatedAt": "2025-01-15T10:30:00"
  }
]
```

---

## Example Workflow

```bash
# Add some tasks
java Main add "Design the homepage"
java Main add "Write unit tests"
java Main add "Deploy to production"

# Start working on task 1
java Main inprogress 1

# Finish task 1
java Main done 1

# Update task 2's description
java Main update 2 "Write unit tests for TaskManager"

# Delete task 3
java Main delete 3

# View all tasks
java Main list
```

---

## Project Structure

```
task-tracker/
├── Main.java            # Entry point — reads commands and routes them
├── TaskManager.java     # Business logic — add, update, delete, mark tasks
├── JsonFileHandler.java # File handling — reads and writes tasks.json
├── Task.java            # Task model — defines what a task looks like
└── tasks.json           # Auto-generated when you add your first task
```

---

## Error Handling

The app handles common errors gracefully:

- Running the program with no command shows the help menu
- Providing an invalid ID (e.g. letters instead of a number) prints a warning
- Using a command without required arguments prints correct usage
- If `tasks.json` does not exist yet, the app starts with an empty task list instead of crashing