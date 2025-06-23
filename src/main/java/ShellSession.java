import java.io.File;

public class ShellSession {
    private static File currentWorkingDirectory = new File(System.getProperty("user.dir"));

    public static File getWD() {
        return currentWorkingDirectory;
    }

    public static void setWD(File wd) {
        currentWorkingDirectory = wd;
    }
}
