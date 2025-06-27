import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private static final List<String> history = new ArrayList<>();
    private static final File historyFile;
    private static int currentIndex = 0;
    private static int fromReadLastIndex = 0;

    static {
        if (System.getenv("HISTFILE") == null) {
            historyFile = null;
        } else {
            historyFile = new File(System.getenv("HISTFILE"));
        }
    }

    public static void addCommand(String command) {
        history.add(command);
        currentIndex++;
    }

    public static void loadCommands() {
        if (historyFile != null) {
            String [] commands = FileHandler.getLinesFromFile(historyFile);
            for (String cmd : commands) {
                addCommand(cmd);
            }
        }
    }

    public static void writeCommands() {
        if (historyFile != null) {
            FileHandler.writeLinesToFile(historyFile, getPreviousCommands());
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
