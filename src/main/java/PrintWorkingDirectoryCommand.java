public class PrintWorkingDirectoryCommand extends BaseCommand {
    public PrintWorkingDirectoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {}

    @Override
    public void execute() {
        Logger.output(ShellSession.getWD().toString());
    }
}
