public class HistoryCommand extends BaseCommand {
    private int _startFrom;
    private String _historyFilePath;
    private boolean _read;
    private boolean _write;
    private boolean _append;

    public HistoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {
        try {
            if (args.length == 1) {
                _startFrom = Integer.parseInt(args[0]);
            } else if (args.length == 2) {
                _historyFilePath = args[1];
                _read = args[0].equals("-r");
                _write = args[0].equals("-w");
                _append = args[0].equals("-a");

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
            String[] cmds = (_startFrom == 0) ? CommandHistory.getAllCommands() : CommandHistory.getAllCommands(_startFrom);

            for (String cmd : cmds) {
                Logger.output(String.format("\t%d %s", count++, cmd));
            }
        } else {
            if (_read) {
                String[] commands = FileHandler.getLinesFromFile(_historyFilePath);
                for (String cmd : commands) {
                    CommandHistory.addCommand(cmd);
                }
            } else if (_write) {
                String[] commands = CommandHistory.getAllCommands();
                FileHandler.writeLinesToFile(_historyFilePath, commands);
            } else if (_append) {
                String[] commands = CommandHistory.getPreviousCommands();
                FileHandler.appendLinesToFile(_historyFilePath, commands);
                CommandHistory.advanceCommandPointer(commands.length);
            }
        }
    }
}
