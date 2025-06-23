public class TypeCommand extends BaseCommand {
    private String _cmdToCheck;

    public TypeCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        _cmdToCheck = args[0];
    }

    @Override
    public void execute() {
        if (isBuiltin(_cmdToCheck)) {
            System.out.printf("%s is a shell builtin\n", _cmdToCheck);
        } else {
            // check if it is an executable
            String path = Executable.findExecutable(_cmdToCheck);
            if (path != null) {
                System.out.printf("%s is %s\n", _cmdToCheck, path);
            } else {
                System.out.printf("%s: not found\n", _cmdToCheck);
            }
        }
    }

    private boolean isBuiltin(String cmd) {
        return BuiltinCommands.getCommands().contains(cmd);
    }
}
