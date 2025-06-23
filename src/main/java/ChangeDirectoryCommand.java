import java.io.File;

public class ChangeDirectoryCommand extends BaseCommand {
    private String _newPath;

    public ChangeDirectoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        _newPath = args[0];
    }

    @Override
    public void execute() {
        File newDirectory = new File(_newPath);

        if (newDirectory.isDirectory()) {
            ShellSession.setWD(newDirectory);
        } else {
            System.out.printf("cd: %s: No such file or directory\n", _newPath);
        }
    }
}
