import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandHistory {
    private static final List<String> history = new ArrayList<>();


    public static void addCommand(String command) {
        history.add(command);
    }

    public static String[] getPreviousCommands() {
        return history.toArray(String[]::new);
    }
}
