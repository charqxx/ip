import java.util.Scanner;
import java.util.ArrayList;
public class Serene {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> history = new ArrayList<>();

        System.out.println("Hello! I'm Serene\nWhat can I do for you?");
        while(true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                for (int i=0; i < history.size(); i++) {
                    System.out.println((i + 1) + ". " + history.get(i));
                }
            } else {
                history.add(input);
                System.out.println("added: " + input);
            }
        }

    }
}
