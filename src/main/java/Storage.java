import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(task.toSaveFormat() +  "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public ArrayList<Task> load() {
        ArrayList<Task> history = new ArrayList<>();
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
