import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandHistory {
    private static final List<String> history = new ArrayList<>();
    private static int currentIndex = 0;
    private static int fromReadLastIndex = 0;

    public static void addCommand(String command) {
        history.add(command);
        currentIndex++;
    }

    public static void loadCommandsFromRead(String[] commands) {
        fromReadLastIndex = currentIndex;
        for (String cmd : commands) {
            addCommand(cmd);
            fromReadLastIndex++;
        }
    }

    public static void advanceCommandPointer(int n) {
        fromReadLastIndex += n;
    }

    public static String[] getAllCommands() {
        return history.toArray(String[]::new);
    }

    public static String[] getAllCommands(int n) {
        return history.subList(history.size() - n, history.size()).toArray(String[]::new);
    }

    public static String[] getPreviousCommands() {
        return history.subList(fromReadLastIndex, history.size()).toArray(String[]::new);
    }

    public static String getUpCommand() {
        if (currentIndex != 0) {
            return history.get(--currentIndex);
        }
        return "";
    }

    public static String getDownCommand() {
        if (currentIndex + 1 != history.size()) {
            return history.get(++currentIndex);
        }
        return "";
    }
}
