import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input;
        String command;

        do {
            System.out.print("$ ");
            input = scanner.nextLine();
            command = getCommand(input);

            switch (command) {
                case "exit" -> System.exit(0);
                case "echo" -> System.out.println(input.substring(4).trim());
                default -> System.out.printf("%s: command not found\n", command);
            }
        } while (true);
    }

    private static String getCommand(String input) {
        return input.split(" ")[0];
    }
}
