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
            ShellSession.setWD(ShellSession.getHome());
            return;
        }

        File newDirectory = FileHandler.getAbsoluteFile(_newPath);

        if (newDirectory.isDirectory()) {
            ShellSession.setWD(newDirectory);
        } else {
            Logger.err(String.format("cd: %s: No such file or directory", _newPath));
        }
    }
}
