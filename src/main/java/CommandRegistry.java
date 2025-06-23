import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandRegistry {
    private static final Map<String, Function<String[], Command>> registry = new HashMap<>();

    public static void register(String commandName, Function<String[], Command> constructor) {
        registry.put(commandName, constructor);
    }

    public static Command create(String commandName, String[] args) {
        Function<String[], Command> ctor = registry.get(commandName);
        if (ctor == null) {
            throw new IllegalArgumentException("Unknown Command");
        }
        return ctor.apply(args);
    }
}
