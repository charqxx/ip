package serene.storage;

import serene.exception.SereneException;
import serene.task.Task;
import serene.task.TaskList;
import serene.task.ToDo;
import serene.task.Deadline;
import serene.task.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path of the file where tasks will be saved and loaded from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates the save file and its parent directories if they do not already exist.
     * If an error occurs during creation, an error message is printed.
     */
    public void createSaveFile() {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdir();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error while creating save file: " + e.getMessage());
        }
    }

    /**
     * Saves the given TaskList to the save file.
     * Each task is written in a format suitable for reloading later.
     *
     * @param tasks The TaskList containing tasks to save.
     */
    public void save(TaskList tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            writeTasksToFile(fw, tasks);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


    /**
     * Writes all tasks in the TaskList to the provided FileWriter.
     *
     * @param fileWriter The FileWriter to write tasks to.
     * @param tasks      The TaskList containing tasks to write.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeTasksToFile(FileWriter fileWriter, TaskList tasks) throws IOException {
        for (int i = 0; i < tasks.size(); i++) {
            fileWriter.write(tasks.get(i).toSaveFormat() + "\n");
        }
        fileWriter.close();
    }

    /**
     * Loads tasks from the save file into a new TaskList.
     * If the file does not exist or an error occurs, an empty TaskList is returned.
     *
     * @return A TaskList containing tasks loaded from the file.
     */
    public TaskList load() throws SereneException{
        TaskList tasks = new TaskList();
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            loadTasksFromFile(sc, tasks);
        } catch (IOException e) {
            throw new SereneException("Something went wrong: " + e.getMessage());
        } catch (SereneException e) {
            throw new SereneException(e.getMessage());
        }
        return tasks;
    }

    /**
     * Reads tasks from a Scanner and adds them to the provided TaskList.
     * Supports ToDo, Deadline, and Event tasks. Marks tasks as done if indicated.
     *
     * @param scanner The Scanner to read task lines from.
     * @param tasks   The TaskList to populate with tasks.
     * @throws SereneException If an invalid task entry is encountered.
     * @throws IOException     If an I/O error occurs while reading from the scanner.
     */
    public void loadTasksFromFile(Scanner scanner, TaskList tasks) throws SereneException, IOException{
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" , ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String TaskName = parts[2];

            if (type.equals("T")) {
                Task task = new ToDo(TaskName);
                if (isDone) task.mark();
                tasks.add(task);
            } else if (type.equals("D")) {
                Task task = new Deadline(TaskName, parts[3]);
                if (isDone) task.mark();
                tasks.add(task);
            } else if (type.equals("E")) {
                String[] fromTo = parts[3].split(" /to ");
                Task task = new Event(TaskName, fromTo[0], fromTo[1]);
                if (isDone) task.mark();
                tasks.add(task);
            } else {
                throw new SereneException("Unable to load file due to invalid entry.");
            }
        }
        scanner.close();
    }

}
