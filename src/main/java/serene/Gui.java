package serene;

import java.util.Scanner;

import java.util.Scanner;

public class Gui {
    private Scanner scanner;

    public Gui() {
        scanner = new Scanner(System.in);
    }

    public String showWelcome() {
        return "Hello! I'm Serene\nWhat can I do for you?";
    }

    public String exitMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

}
