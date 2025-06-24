import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class Main {
    static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        String input;
        CommandInvoker invoker = new CommandInvoker();
        OutputRedirector redirector = new OutputRedirector();

        BuiltinCommands.registerAll();  // register all builtin commands

        while (true) {
            System.out.print("$ ");
            input = reader.nextLine();
            String command = UserInputParser.getCommand(input);
            String[] arguments = UserInputParser.getArgs(input);
            String stream = UserInputParser.getOutputStream(input);

            redirector.redirectTo((stream == null) ? null : new File(stream));
            redirector.setStream();

            try {
                Command cmd = CommandRegistry.create(command, arguments);
                invoker.setCommand(cmd);
                invoker.invoke();
            } catch (IllegalArgumentException e) {
                String executable = Executable.findExecutable(command);

                if (executable != null) {
                    invoker.setCommand(new ExecutableCommand(command, arguments));
                    invoker.invoke();
                } else {
                    System.out.printf("%s: command not found\n", command);
                }
            }

            redirector.redirectTo(null);
            redirector.setStream();
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
