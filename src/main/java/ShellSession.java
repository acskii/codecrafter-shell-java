import java.io.File;

public class ShellSession {
    private static final File homeDirectory;
    private static final String pathDirectory;
    private static File currentWorkingDirectory = new File(System.getProperty("user.dir"));

    static {
        if (System.getenv("HOME") == null) {
            homeDirectory = currentWorkingDirectory;
        } else {
            homeDirectory = new File(System.getenv("HOME"));
        }

        if (System.getenv("PATH") == null) {
            pathDirectory = null;
        } else {
            pathDirectory = System.getenv("PATH");
        }
    }

    public static File getWD() {
        return currentWorkingDirectory;
    }

    public static String getPath() {
        return pathDirectory;
    }

    public static File getHome() {
        return homeDirectory;
    }

    public static void setWD(File wd) {
        currentWorkingDirectory = wd;
    }
}
