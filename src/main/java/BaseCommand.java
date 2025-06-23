public abstract class BaseCommand implements Command {
    public BaseCommand(String[] args) {
        setArgs(args);
    }

    public abstract void setArgs(String[] args);
    public abstract void execute();
}
