import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
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
                case "type" -> printType(input.split(" ")[1]);
                default -> System.out.printf("%s: command not found\n", command);
            }
        } while (true);
    }

    private static String getCommand(String input) {
        return input.split(" ")[0];
    }

    private static void printType(String command) {
        switch (command) {
            case "echo":
            case "type":
            case "exit":
                System.out.printf("%s is a shell builtin\n", command);
                break;
            default:
                printExecutableType(command);
        }
    }

    private static void printExecutableType(String command) {
        String path = System.getenv("PATH");
        String[] dirs = path.split(File.pathSeparator);
        boolean exists = false;

        for (String dir : dirs) {
            File cmd = new File(dir, command);
            if (cmd.exists()) {
                exists = true;
                System.out.printf("%s is %s\n", command, cmd.getAbsolutePath());
                break;
            }
        }

        if (!exists) System.out.printf("%s: not found\n", command);
    }
}
