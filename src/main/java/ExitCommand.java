public class ExitCommand extends BaseCommand {
    private int _statusCode;

    public ExitCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        try {
            _statusCode = Integer.parseInt(args[0]);    // first arg is the status code (for now)
        } catch (NumberFormatException e) {
            System.out.println("ERROR");   // error handler
        }
    }

    @Override
    public void execute() {
        System.exit(_statusCode);
    }
}
