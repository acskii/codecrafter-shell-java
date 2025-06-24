import java.io.File;

public class ShellSession {
    private static final File homeDirectory;
    private static File currentWorkingDirectory = new File(System.getProperty("user.dir"));

    static {
        if (System.getenv("HOME") == null) {
            homeDirectory = currentWorkingDirectory;
        } else {
            homeDirectory = new File(System.getenv("HOME"));
        }
    }

    public static File getWD() {
        return currentWorkingDirectory;
    }

    public static File getHome() {
        return homeDirectory;
    }

    public static void setWD(File wd) {
        currentWorkingDirectory = wd;
    }
}
