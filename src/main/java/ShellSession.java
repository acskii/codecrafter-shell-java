import java.io.File;

public class ShellSession {
    private static final File homeDirectory = new File(System.getenv("HOME"));
    private static File currentWorkingDirectory = new File(System.getProperty("user.dir"));

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
