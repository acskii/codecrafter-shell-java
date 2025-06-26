import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
//    private static final Scanner reader = new Scanner(System.in);
    private static Process rawMode;

    public static void main(String[] args) {
        CommandInvoker invoker = new CommandInvoker();
        BuiltinCommands.registerAll();  // register all builtin commands

        enableRawMode();

        try {
            while (true) {
                String input = read();
                String command = UserInputParser.getCommand(input);
                String[] arguments = UserInputParser.getArgs(input);
                String stream = UserInputParser.getOutputStream(input);

                CommandHistory.addCommand(input);
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
        } finally {
            disableRawMode();
        }
    }

    private static void enableRawMode() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", "stty -echo -icanon min 1 < /dev/tty");
            processBuilder.directory(new File("").getCanonicalFile());
            rawMode = processBuilder.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    new ProcessBuilder("/bin/sh", "-c", "stty sane < /dev/tty").start().waitFor();
                } catch (Exception e) {
                    Logger.err("Failed to reset terminal: " + e);
                }
            }));
        } catch (IOException e) {
            Logger.err(e.toString());
        }
    }

    private static void disableRawMode() {
        try {
            if (rawMode != null && rawMode.isAlive()) {
                new ProcessBuilder("/bin/sh", "-c", "stty sane < /dev/tty")
                        .inheritIO()
                        .start()
                        .waitFor();
                rawMode.destroy();
            }
        } catch (Exception e) {
            Logger.err(e.toString());
        }
    }

    private static void clearLine(int spaces) {
        System.out.print('\r');
        for (int i = 0; i < spaces; i++) System.out.print(" ");
        System.out.print('\r');
    }

    private static String read() {
        try {
            System.out.print("$ ");
            StringBuilder buffer = new StringBuilder();

            while (true) {
                int input = System.in.read();

                if (input == -1) {
                    return "";
                }
                char character = (char) input;

                switch (character) {
                    case '\r':
                        break;

                    case '\n': {
                        System.out.print('\n');
                        return buffer.toString();
                    }

                    case 0x7f: {
                        // Backspace
                        if (buffer.isEmpty()) continue;
                        buffer.deleteCharAt(buffer.length());
                        System.out.print("\b \b");
                        break;
                    }

                    // UP key is ESC [ A
                    // DOWN key is ESC [ B

                    case 0x1b: {
                        // ESC
                        int bracket = System.in.read();
                        int code = System.in.read();

                        if (bracket == 91) {
                            // [
                            if (code == 65) {
                                // A -> UP KEY
                                String previous = CommandHistory.getUpCommand();
                                clearLine(2 + buffer.length());
                                System.out.print("$ ");
                                System.out.print(previous);

                                buffer.delete(0, buffer.length());
                                buffer.append(previous);
                                break;
                            } else if (code == 66) {
                                // B -> DOWN KEY
                                String previous = CommandHistory.getDownCommand();
                                clearLine(2 + buffer.length());
                                System.out.print("$ ");
                                System.out.print(previous);

                                buffer.delete(0, buffer.length());
                                buffer.append(previous);
                                break;
                            }
                        }
                        break;
                    }

                    default: {
                        buffer.append(character);
                        System.out.print(character);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            Logger.err(e.toString());
        }

        return "";
    }
}
