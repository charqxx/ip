package serene.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Serene\nWhat can I do for you?");
    }

    public void exitMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
