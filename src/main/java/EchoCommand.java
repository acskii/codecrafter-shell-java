public class EchoCommand extends BaseCommand{
    private String[] _argsToPrint;

    public EchoCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        _argsToPrint = args;
    }

    @Override
    public void execute() {
        for (String arg : _argsToPrint) {
            System.out.printf("%s ", arg);
        }
        System.out.print("\n");
    }
}
