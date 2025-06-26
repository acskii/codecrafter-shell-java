import java.util.Scanner;

public class Main {
    private static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {

        CommandInvoker invoker = new CommandInvoker();

        BuiltinCommands.registerAll();  // register all builtin commands

        while (true) {
            System.out.print("$ ");
            String input = reader.nextLine();
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

            CommandHistory.addCommand(command);
            Logger.set(null, null);
        }
    }
}
