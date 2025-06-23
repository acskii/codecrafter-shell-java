public class PrintWorkingDirectoryCommand extends BaseCommand {
    public PrintWorkingDirectoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {}

    @Override
    public void execute() {
        System.out.println(System.getProperty("user.dir"));
    }
}
