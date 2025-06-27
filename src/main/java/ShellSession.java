import java.io.File;

public class ShellSession {
    private static final File homeDirectory;
    private static final File pathDirectory;
    private static File currentWorkingDirectory = new File(System.getProperty("user.dir"));

    static {
        if (System.getenv("HOME") == null) {
            homeDirectory = currentWorkingDirectory;
        } else {
            homeDirectory = new File(System.getenv("HOME"));
        }

        if (System.getenv("PATH") == null) {
            pathDirectory = currentWorkingDirectory;
        } else {
            pathDirectory = new File(System.getenv("PATH"));
        }
    }

    public static File getWD() {
        return currentWorkingDirectory;
    }

    public static File getPath() {
        return pathDirectory;
    }

    public static File getHome() {
        return homeDirectory;
    }

    public static void setWD(File wd) {
        currentWorkingDirectory = wd;
    }
}
