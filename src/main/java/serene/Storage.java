package serene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toSaveFormat() +  "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public TaskList load() {
        TaskList history = new TaskList();
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" , ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String TaskName = parts[2];

                if (type.equals("T")) {
                    Task task = new ToDo(TaskName);
                    if (isDone) task.mark();
                    history.add(task);
                } else if (type.equals("D")) {
                    Task task = new Deadline(TaskName, parts[3]);
                    if (isDone) task.mark();
                    history.add(task);
                } else if (type.equals("E")) {
                    String[] fromTo = parts[3].split(" /to ");
                    Task task = new Event(TaskName, fromTo[0], fromTo[1]);
                    if (isDone) task.mark();
                    history.add(task);
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return history;
    }

}
