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
            Logger.output(String.format("%s is a shell builtin", _cmdToCheck));
        } else {
            // check if it is an executable
            String path = Executable.findExecutable(_cmdToCheck);
            if (path != null) {
                Logger.output(String.format("%s is %s", _cmdToCheck, path));
            } else {
                Logger.err(String.format("%s: not found", _cmdToCheck));
            }
        }
    }

    private boolean isBuiltin(String cmd) {
        return BuiltinCommands.getCommands().contains(cmd);
    }
}
