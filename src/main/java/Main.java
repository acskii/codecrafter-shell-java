import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class Main {
    static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        String input;
        CommandInvoker invoker = new CommandInvoker();

        BuiltinCommands.registerAll();  // register all builtin commands

        while (true) {
            System.out.print("$ ");
            input = reader.nextLine();
            String command = UserInputParser.getCommand(input);
            String[] arguments = UserInputParser.getArgs(input);

            try {
                Command cmd = CommandRegistry.create(command, arguments);
                invoker.setCommand(cmd);
                invoker.invoke();
            } catch (IllegalArgumentException e) {
                System.out.printf("%s: command not found\n", command);
            }
        }

//        do {
//            System.out.print("$ ");
//            input = reader.nextLine();
//            command = getCommand(input);
//
//            switch (command) {
//                case "exit" -> System.exit(0);
//                case "echo" -> System.out.println(input.substring(4).trim());
//                case "type" -> printType(input.split(" ")[1]);
//                default:
//                    if (!runIfExecutable(command, getArguments(input))) {
//                        System.out.printf("%s: command not found\n", command);
//                    }
//            }
//        } while (true);
    }
}
