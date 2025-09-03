package serene.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String showWelcome() {
        return "Hello! I'm Serene\nWhat can I do for you?";
    }

    public String exitMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public String getUserInput() {
        //System.out.println(prompt + ":");
        return scanner.nextLine();
    }

    public String showMessage(String msg) {
        return msg;
    }
}
