import java.util.ArrayList;
import java.util.function.Function;
import java.util.List;

public enum BuiltinCommands {
    /* ADD BUILTIN COMMANDS BELOW HERE */
    ECHO("echo", EchoCommand::new),
    EXIT("exit", ExitCommand::new),
    TYPE("type", TypeCommand::new),
    CD("cd", ChangeDirectoryCommand::new),
    HISTORY("history", HistoryCommand::new),
    PWD("pwd", PrintWorkingDirectoryCommand::new);
    /* ADD BUILTIN COMMANDS ABOVE HERE */

    private final String name;
    private final Function<String[], Command> ctor;

    BuiltinCommands(String name, Function<String[], Command> ctor) {
        this.name = name;
        this.ctor = ctor;
    }

    public static void registerAll() {
        for (BuiltinCommands cmd : values()) {
            CommandRegistry.register(cmd.name, cmd.ctor);
        }
    }

    public static List<String> getCommands() {
        List<String> list = new ArrayList<>();

        for (BuiltinCommands cmd : values()) {
            list.add(cmd.name);
        }

        return list;
    }
}
