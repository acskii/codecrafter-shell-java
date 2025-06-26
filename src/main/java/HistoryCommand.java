public class HistoryCommand extends BaseCommand {
    private int _startFrom;

    public HistoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        try {
            if (args.length > 0) {
                _startFrom = Integer.parseInt(args[0]);
            } else {
                _startFrom = 0;
            }
        } catch (NumberFormatException e) {
            _startFrom = 0;
        }
    }

    @Override
    public void execute() {
        int count = 1 + _startFrom;
        String[] cmds = (_startFrom == 0) ? CommandHistory.getPreviousCommands() : CommandHistory.getPreviousCommands(_startFrom);

        for (String cmd : cmds) {
            Logger.output(String.format("\t%d %s", count++, cmd));
        }
    }
}
