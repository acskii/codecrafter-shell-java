import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Executable {
    public static String findExecutable(String exe) {
        String path = ShellSession.getPath();
        if (path == null) return null;

        String[] dirs = path.split(File.pathSeparator);

        for (String dir : dirs) {
            File cmd = new File(dir, exe);
            if (cmd.exists() && cmd.canExecute()) {
                return cmd.getAbsolutePath();
            }
        }

        return null;
    }

    public static String[] getExecutablesFromPath() {
        String path = ShellSession.getPath();
        if (path == null) return null;

        String[] dirs = path.split(File.pathSeparator);
        List<String> executables = new ArrayList<>();

        for (String dir : dirs) {
            File directory = new File(dir);
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files == null) break;

                for (File file : files) {
                    if (file != null && file.isFile()) {
                        executables.add(file.getName());
                    }
                }
            }
        }

        return executables.toArray(String[]::new);
    }
}
