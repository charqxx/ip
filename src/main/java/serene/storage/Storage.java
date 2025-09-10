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

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void save(TaskList tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            writeTasksToFile(fw, tasks);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void writeTasksToFile(FileWriter fileWriter, TaskList tasks) throws IOException {
        for (int i = 0; i < tasks.size(); i++) {
            fileWriter.write(tasks.get(i).toSaveFormat() + "\n");
        }
        fileWriter.close();
    }

    public TaskList load() {
        TaskList tasks = new TaskList();
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            loadTasksFromFile(sc, tasks);
            return tasks;
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (SereneException e) {
            System.out.println(e);
        }
    }

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
