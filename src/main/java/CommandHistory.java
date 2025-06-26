import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandHistory {
    private static final List<String> history = new ArrayList<>();
    private static final Stack<String> upHistory = new Stack<>();

    public static void addCommand(String command) {
        history.add(command);
        upHistory.push(command);
    }

    public static String[] getPreviousCommands() {
        return history.toArray(String[]::new);
    }

    public static String[] getPreviousCommands(int n) {
        return history.subList(history.size() - n, history.size()).toArray(String[]::new);
    }

    public static String getLatestCommand() {
        return upHistory.pop();
    }
}
