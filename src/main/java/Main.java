import java.util.Scanner;
import java.io.File;

public class Main {
    static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        String input;
        CommandInvoker invoker = new CommandInvoker();
//        OutputRedirector redirector = new OutputRedirector();

        BuiltinCommands.registerAll();  // register all builtin commands

        while (true) {
            System.out.print("$ ");
            input = reader.nextLine();
            String command = UserInputParser.getCommand(input);
            String[] arguments = UserInputParser.getArgs(input);
            String stream = UserInputParser.getOutputStream(input);

            Logger.setAppend(UserInputParser.isAppendRedirector(input));

            if (UserInputParser.hasOutputRedirector(input)) {
                Logger.set(stream, null);
            } else if (UserInputParser.hasErrRedirector(input)) {
                Logger.set(null, stream);
            }

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
                    Logger.err(String.format("%s: command not found", command));
                }
            }

            Logger.set(null, null);
        }
    }
}
