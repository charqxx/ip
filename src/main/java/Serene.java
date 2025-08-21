import java.util.Scanner;
public class Serene {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Serene\nWhat can I do for you?");
        while(true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println(input);
            }
        }


    }
}
