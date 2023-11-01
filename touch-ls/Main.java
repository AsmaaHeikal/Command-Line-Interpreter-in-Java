import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                System.out.println("Exiting");
                break; 
            }

            terminal.parseAndExecute(input);
        }
        scanner.close();
    }
}
