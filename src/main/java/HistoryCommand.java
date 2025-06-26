public class HistoryCommand extends BaseCommand {
    private int _startFrom;
    private String _historyFilePath;

    public HistoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        try {
            if (args.length == 1) {
                _startFrom = Integer.parseInt(args[0]);
            } else if (args.length == 2 && args[0].equals("-r")) {
                _historyFilePath = args[1];
            } else {
                _startFrom = 0;
            }
        } catch (NumberFormatException e) {
            _startFrom = 0;
        }
    }

    @Override
    public void execute() {
        if (_historyFilePath == null) {
            int count = 1 + _startFrom;
            String[] cmds = (_startFrom == 0) ? CommandHistory.getPreviousCommands() : CommandHistory.getPreviousCommands(_startFrom);

            for (String cmd : cmds) {
                Logger.output(String.format("\t%d %s", count++, cmd));
            }
        } else {
            String[] commands = FileHandler.getLinesFromFile(_historyFilePath);
            for (String cmd : commands) {
                CommandHistory.addCommand(cmd);
            }
        }
    }
}
