public class HistoryCommand extends BaseCommand {
    public HistoryCommand(String[] args) {
        super(args);
    }

    @Override
    public void setArgs(String[] args) {}

    @Override
    public void execute() {
        int count = 1;
        for (String cmd : CommandHistory.getPreviousCommands()) {
            Logger.output(String.format("\t%d %s", count++, cmd));
        }
    }
}
