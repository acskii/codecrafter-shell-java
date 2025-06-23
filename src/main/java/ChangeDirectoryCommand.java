import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeDirectoryCommand extends BaseCommand {
    private String _newPath;

    public ChangeDirectoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        // if no path is provided, change to home directory '~'
        _newPath = ((args.length >= 1) ? args[0] : "~");
    }

    @Override
    public void execute() {
        if (_newPath.startsWith("~")) {
            ShellSession.setWD(new File(System.getProperty("user.home")));
            return;
        }

        String pwd = ShellSession.getWD().getAbsolutePath();
        Path path = Paths.get(pwd);
        Path resolvedPath = path.resolve(_newPath);

        File newDirectory = new File(resolvedPath.normalize().toString());

        if (newDirectory.isDirectory()) {
            ShellSession.setWD(newDirectory);
        } else {
            System.out.printf("cd: %s: No such file or directory\n", _newPath);
        }
    }
}
