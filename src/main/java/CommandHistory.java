import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandHistory {
    private static final Stack<String> history = new Stack<>();


    public static void addCommand(String command) {
        history.push(command);
    }

    public static String[] getPreviousCommands() {
        List<String> cmds = new ArrayList<>();
        Stack<String> temp = new Stack<>();

        while (!history.isEmpty()) {
            String cmd = history.pop();
            cmds.add(cmd);
            temp.push(cmd);
        }

        while (!temp.isEmpty()) { history.push(temp.pop()); }
        return cmds.toArray(String[]::new);
    }
}
