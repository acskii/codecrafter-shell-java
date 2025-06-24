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
            redirector.setSystemStream();

            try {
                Command cmd = CommandRegistry.create(command, arguments);
                invoker.setCommand(cmd);
                invoker.invoke();
            } catch (IllegalArgumentException e) {
                String executable = Executable.findExecutable(command);

                if (executable != null) {
                    invoker.setCommand(new ExecutableCommand(command, arguments, redirector.getCurrentOutput()));
                    invoker.invoke();
                } else {
                    System.out.printf("%s: command not found\n", command);
                }
            }

            redirector.redirectTo(null);
            redirector.setSystemStream();
        }
    }
}
