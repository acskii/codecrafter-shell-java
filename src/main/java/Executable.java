import java.io.File;

public class Executable {
    public static String findExecutable(String exe) {
        String path = System.getenv("PATH");
        if (path == null) return null;

        String[] dirs = path.split(File.pathSeparator);

        for (String dir : dirs) {
            File cmd = new File(dir, exe);
            if (cmd.exists()) {
                return cmd.getAbsolutePath();
            }
        }

        return null;
    }
}
